package com.fawry.moviesdb.data.cache

import com.fawry.moviesdb.data.cache.model.CachedMovie
import com.fawry.moviesdb.domain.model.category.Category
import kotlinx.coroutines.flow.Flow

interface Cache {

    fun getPopularMovies(): Flow<List<CachedMovie>>
    fun getTopRatedMovies(): Flow<List<CachedMovie>>
    fun getUpcomingMovies(): Flow<List<CachedMovie>>

    suspend fun getMovieById(id: Long): CachedMovie?

    suspend fun storeMovies(category: Category, movies: List<CachedMovie>)
}