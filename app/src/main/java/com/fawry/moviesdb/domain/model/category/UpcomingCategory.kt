package com.fawry.moviesdb.domain.model.category

import com.fawry.moviesdb.data.api.MoviesApi
import com.fawry.moviesdb.data.api.model.ApiPaginatedMovies
import com.fawry.moviesdb.data.cache.Cache
import com.fawry.moviesdb.data.cache.model.CachedMovie
import kotlinx.coroutines.flow.Flow

class UpcomingCategory : Category {
    override fun setCacheCategoryValue(movie: CachedMovie): CachedMovie {
        return  movie.copy(isUpcoming = true)
    }

    override suspend fun apiCall(api: MoviesApi, page: Int): ApiPaginatedMovies {
        return api.getUpComingMovies(page)
    }
    override fun getCache(cache: Cache): Flow<List<CachedMovie>> {
        return cache.getUpcomingMovies()
    }
}