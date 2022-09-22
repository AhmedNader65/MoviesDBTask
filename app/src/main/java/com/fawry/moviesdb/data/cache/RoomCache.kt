package com.fawry.moviesdb.data.cache

import com.fawry.moviesdb.data.cache.daos.MoviesDao
import com.fawry.moviesdb.data.cache.model.CachedMovie
import com.fawry.moviesdb.domain.model.category.Category
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomCache @Inject constructor(
    private val moviesDao: MoviesDao,
) : Cache {
    override fun getPopularMovies(): Flow<List<CachedMovie>> {
        return moviesDao.getPopularMovies()
    }

    override fun getTopRatedMovies(): Flow<List<CachedMovie>> {
        return moviesDao.getTopRatedMovies()
    }

    override fun getUpcomingMovies(): Flow<List<CachedMovie>> {
        return moviesDao.getUpcomingMovies()
    }

    override suspend fun getMovieById(id: Long): CachedMovie? {
        return moviesDao.getMovieById(id)
    }

    override suspend fun storeMovies(category: Category,movies: List<CachedMovie>) {
        moviesDao.insertOrUpdateMovie(category ,*movies.toTypedArray())
    }
}