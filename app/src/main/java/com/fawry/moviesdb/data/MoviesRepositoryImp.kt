package com.fawry.moviesdb.data

import com.fawry.moviesdb.data.api.MoviesApi
import com.fawry.moviesdb.data.api.model.mapToDomain
import com.fawry.moviesdb.data.cache.Cache
import com.fawry.moviesdb.data.cache.model.CachedMovie
import com.fawry.moviesdb.data.cache.model.toDomain
import com.fawry.moviesdb.domain.model.Movie
import com.fawry.moviesdb.domain.model.NetworkException
import com.fawry.moviesdb.domain.model.PaginatedMovies
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

    override fun getMovies(): Flow<List<Movie>> {
        return cache.getMovies()
            .distinctUntilChanged() // ensures only events with new information get to the subscriber.
            .map { moviesList ->
                moviesList.map { it.toDomain() }
            }
    }

    override suspend fun requestMoreMovies(
        pageToLoad: Int
    ): PaginatedMovies {
        try {
            val results = api.getPopularMovies(pageToLoad)
            return results.mapToDomain()
        } catch (exception: HttpException) {
            throw NetworkException(exception.message ?: "Code ${exception.code()}")
        }
    }

    override suspend fun storeMovies(movies: List<Movie>) {
        cache.storeMovies(movies.map { CachedMovie.fromDomain(it) })
    }
}