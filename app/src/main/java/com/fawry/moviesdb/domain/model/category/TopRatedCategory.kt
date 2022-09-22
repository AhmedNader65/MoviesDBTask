package com.fawry.moviesdb.domain.model.category

import com.fawry.moviesdb.data.api.MoviesApi
import com.fawry.moviesdb.data.api.model.ApiPaginatedMovies
import com.fawry.moviesdb.data.cache.Cache
import com.fawry.moviesdb.data.cache.model.CachedMovie
import kotlinx.coroutines.flow.Flow

class TopRatedCategory : Category {
    override fun setCacheCategoryValue(movie: CachedMovie): CachedMovie {
        return  movie.copy(isTopRated = true)
    }

    override suspend fun apiCall(api: MoviesApi, page: Int): ApiPaginatedMovies {
        return api.getTopRatedMovies(page)
    }

    override fun getCache(cache: Cache): Flow<List<CachedMovie>> {
        return cache.getTopRatedMovies()
    }
}