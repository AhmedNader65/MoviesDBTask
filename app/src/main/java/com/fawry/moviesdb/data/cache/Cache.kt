package com.fawry.moviesdb.data.cache

import com.fawry.moviesdb.data.cache.model.CachedMovie
import kotlinx.coroutines.flow.Flow

interface Cache {

    fun getMovies(): Flow<List<CachedMovie>>

    suspend fun getMovieById(id: Long): CachedMovie

    suspend fun storeMovies(movies: List<CachedMovie>)
}