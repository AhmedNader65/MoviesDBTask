package com.fawry.moviesdb.data.cache

import com.fawry.moviesdb.data.cache.model.CachedMovie
import kotlinx.coroutines.flow.Flow

class RoomCache : Cache {
    override fun getMovies(): Flow<List<CachedMovie>> {
        TODO("Not yet implemented")
    }

    override suspend fun storeMovies(movies: List<CachedMovie>) {
        TODO("Not yet implemented")
    }
}