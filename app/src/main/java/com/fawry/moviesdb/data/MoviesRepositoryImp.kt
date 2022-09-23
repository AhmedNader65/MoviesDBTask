package com.fawry.moviesdb.data

import com.fawry.moviesdb.data.api.MoviesApi
import com.fawry.moviesdb.data.api.model.mapToDomain
import com.fawry.moviesdb.data.cache.Cache
import com.fawry.moviesdb.data.cache.model.CachedMovie
import com.fawry.moviesdb.data.cache.model.toDomain
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

    override fun getMovies(category: Category): Flow<List<Movie>> {
        return category.getCache(cache)
            .distinctUntilChanged() // ensures only events with new information get to the subscriber.
            .map { moviesList ->
                moviesList.map { it.toDomain() }
            }
    }

    override suspend fun getMovieById(id: Long): Movie {
        return cache.getMovieById(id)!!.toDomain()
    }

    override suspend fun requestMoreMovies(
        category: Category,
        pageToLoad: Int
    ): PaginatedMovies {
        try {

            val results = category.apiCall(api, pageToLoad)
            return results.mapToDomain()
        } catch (exception: HttpException) {
            throw NetworkException(exception.message ?: "Code ${exception.code()}")
        }
    }

    override suspend fun storeMovies(category: Category, movies: List<Movie>) {
        movies.forEach {
            val itemFromDB = cache.getMovieById(it.id)
            if (itemFromDB == null) {
                val updated = category.setCacheCategoryValue(CachedMovie.fromDomain(it))
                cache.storeMovies(updated)
            } else {
                cache.storeMovies(itemFromDB)
            }
        }
    }
}