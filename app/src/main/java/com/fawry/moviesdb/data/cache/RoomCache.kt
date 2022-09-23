package com.fawry.moviesdb.data.cache

import androidx.paging.PagingSource
import com.fawry.moviesdb.data.cache.daos.MoviesDao
import com.fawry.moviesdb.data.cache.model.CachedMovie
import com.fawry.moviesdb.domain.model.category.Category
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomCache @Inject constructor(
    private val moviesDao: MoviesDao,
) : Cache {
    override fun getPopularPagingSource(): PagingSource<Int, CachedMovie> {
        return moviesDao.popularPagingSource()
    }

    override fun getUpcomingPagingSource(): PagingSource<Int, CachedMovie> {
        return moviesDao.upcomingPagingSource()
    }

    override fun getTopRatedPagingSource(): PagingSource<Int, CachedMovie> {
        return moviesDao.topRatedPagingSource()
    }

    override suspend fun getPopularMoviesCount(): Int {
        return moviesDao.getPopularMoviesCount()
    }

    override suspend fun getTopRatedMoviesCount(): Int {
        return moviesDao.getTopRatedMoviesCount()
    }

    override suspend fun getUpcomingMoviesCount(): Int {
        return moviesDao.getUpcomingMoviesCount()
    }


    override suspend fun getMovieById(id: Long): CachedMovie? {
        return moviesDao.getMovieById(id)
    }

    override suspend fun getCreatedAt(): Long {
        return moviesDao.getCreatedAt()
    }

    override suspend fun storeMovies(vararg movie: CachedMovie) {
        moviesDao.insertMovie(*movie)
    }

    override suspend fun deleteAll() {
        moviesDao.deleteAll()
    }
}