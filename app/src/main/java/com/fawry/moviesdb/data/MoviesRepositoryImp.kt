package com.fawry.moviesdb.data

import androidx.paging.*
import com.fawry.moviesdb.data.api.ApiParameters.PAGE_SIZE
import com.fawry.moviesdb.data.api.MoviesApi
import com.fawry.moviesdb.data.api.model.mapToDomain
import com.fawry.moviesdb.data.cache.Cache
import com.fawry.moviesdb.data.cache.model.CachedMovie
import com.fawry.moviesdb.data.cache.model.toDomain
import com.fawry.moviesdb.data.paging.MoviesRemoteMediator
import com.fawry.moviesdb.domain.model.Movie
import com.fawry.moviesdb.domain.model.NetworkException
import com.fawry.moviesdb.domain.model.PaginatedMovies
import com.fawry.moviesdb.domain.model.category.Category
import com.fawry.moviesdb.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import javax.inject.Inject

class MoviesRepositoryImp @Inject constructor(
    private val cache: Cache,
    private val api: MoviesApi
) : MoviesRepository {

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getMovies(category: Category): Flow<PagingData<Movie>> {
        val pager = Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            remoteMediator = MoviesRemoteMediator(
                category, cache, api
            )
        ) {
            category.getCache(cache)
        }
        return pager.flow.map { data -> data.map { it.toDomain() } }
    }

    override suspend fun getMovieById(id: Long): Movie {
        return cache.getMovieById(id)!!.toDomain()
    }

}