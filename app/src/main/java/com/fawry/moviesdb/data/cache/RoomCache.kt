package com.fawry.moviesdb.data.cache

import com.fawry.moviesdb.data.cache.model.CachedMovie
import io.reactivex.Flowable

class RoomCache : Cache {
    override fun getMovies(): Flowable<List<CachedMovie>> {
        TODO("Not yet implemented")
    }

    override suspend fun storeMovies(movies: List<CachedMovie>) {
        TODO("Not yet implemented")
    }
}