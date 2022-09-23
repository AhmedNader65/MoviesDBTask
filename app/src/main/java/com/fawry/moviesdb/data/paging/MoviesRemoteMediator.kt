package com.fawry.moviesdb.data.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.fawry.moviesdb.data.api.ApiParameters.FIRST_PAGE
import com.fawry.moviesdb.data.api.MoviesApi
import com.fawry.moviesdb.data.api.model.ApiMovies
import com.fawry.moviesdb.data.api.model.mapToDomain
import com.fawry.moviesdb.data.cache.Cache
import com.fawry.moviesdb.data.cache.MoviesDatabase
import com.fawry.moviesdb.data.cache.RoomCache
import com.fawry.moviesdb.data.cache.model.CachedMovie
import com.fawry.moviesdb.data.cache.model.toDomain
import com.fawry.moviesdb.domain.model.category.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class MoviesRemoteMediator(
    private val category: Category,
    private val cache: Cache,
    private val moviesApi: MoviesApi
) : RemoteMediator<Int, CachedMovie>() {


    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(4, TimeUnit.HOURS)
        return if (System.currentTimeMillis() - cache.getCreatedAt() <= cacheTimeout) {
            // Cached data is up-to-date, so there is no need to re-fetch
            // from the network.
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            // Need to refresh cached data from network; returning
            // LAUNCH_INITIAL_REFRESH here will also block RemoteMediator's
            // APPEND and PREPEND from running until REFRESH succeeds.
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CachedMovie>
    ): MediatorResult {
        return try {

            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    withContext(Dispatchers.IO) {
                        val count = category.getItemsCount(cache)
                        Log.e("TAG", "load: $count")
                        return@withContext (count / 20) + 1
                    }
                }
            }

            // Suspending network load via Retrofit. This doesn't need to be
            // wrapped in a withContext(Dispatcher.IO) { ... } block since
            // Retrofit's Coroutine CallAdapter dispatches on a worker
            // thread.

            val response = category.apiCall(moviesApi, loadKey.toLong())
            if (loadType == LoadType.REFRESH) {
                cache.deleteAll()
            }

            // Insert new movies into database, which invalidates the
            // current PagingData, allowing Paging to present the updates
            // in the DB.
            response.movies.forEach {
                val itemFromDB = cache.getMovieById(it.id!!)
                if (itemFromDB == null) {
                    val updated =
                        category.setCacheCategoryValue(CachedMovie.fromDomain(it.mapToDomain()))
                    cache.storeMovies(updated)
                } else {
                    val updated =
                        category.setCacheCategoryValue(itemFromDB)
                    updated.modifiedAt = System.currentTimeMillis()
                    cache.storeMovies(updated)
                }
            }
            MediatorResult.Success(
                endOfPaginationReached = response.currentPage == response.totalPages
            )
        } catch (e: IOException) {
            e.printStackTrace()
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            e.printStackTrace()
            MediatorResult.Error(e)
        } catch (e: Exception) {
            e.printStackTrace()
            MediatorResult.Error(e)
        }
    }
}