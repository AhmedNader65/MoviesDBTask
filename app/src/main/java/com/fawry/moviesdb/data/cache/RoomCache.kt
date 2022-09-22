package com.fawry.moviesdb.data.cache

import com.fawry.moviesdb.data.cache.daos.MoviesDao
import com.fawry.moviesdb.data.cache.model.CachedMovie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomCache @Inject constructor(
    private val moviesDao: MoviesDao,
) : Cache {
    override fun getMovies(): Flow<List<CachedMovie>> {
        return moviesDao.getAllMovies()
    }

    override suspend fun storeMovies(movies: List<CachedMovie>) {
        moviesDao.insertMovie(*movies.toTypedArray())
    }
}