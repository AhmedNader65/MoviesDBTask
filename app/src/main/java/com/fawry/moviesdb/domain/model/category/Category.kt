package com.fawry.moviesdb.domain.model.category

import com.fawry.moviesdb.data.api.MoviesApi
import com.fawry.moviesdb.data.api.model.ApiPaginatedMovies
import com.fawry.moviesdb.data.cache.Cache
import com.fawry.moviesdb.data.cache.model.CachedMovie
import kotlinx.coroutines.flow.Flow

interface Category {
    fun setCacheCategoryValue(movie: CachedMovie): CachedMovie
    suspend fun apiCall(api: MoviesApi, page: Int): ApiPaginatedMovies
    fun getCache(cache: Cache): Flow<List<CachedMovie>>
}