package com.fawry.moviesdb.data.cache

import com.fawry.moviesdb.data.cache.model.CachedMovie
import io.reactivex.Flowable

interface Cache {

    fun getMovies(): Flowable<List<CachedMovie>>

    suspend fun storeMovies(movies: List<CachedMovie>)
}