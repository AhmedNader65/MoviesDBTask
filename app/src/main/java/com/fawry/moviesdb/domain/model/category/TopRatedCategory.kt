package com.fawry.moviesdb.domain.model.category

import androidx.core.view.isVisible
import androidx.paging.PagingSource
import com.fawry.moviesdb.data.api.MoviesApi
import com.fawry.moviesdb.data.api.model.ApiPaginatedMovies
import com.fawry.moviesdb.data.cache.Cache
import com.fawry.moviesdb.data.cache.model.CachedMovie
import com.fawry.moviesdb.databinding.FragmentMoviesBinding

class TopRatedCategory : Category {
    override fun setCacheCategoryValue(movie: CachedMovie): CachedMovie {
        return movie.copy(isTopRated = true)
    }

    override suspend fun apiCall(api: MoviesApi, page: Long): ApiPaginatedMovies {
        return api.getTopRatedMovies(page)
    }

    override fun getCache(cache: Cache): PagingSource<Int, CachedMovie> {
        return cache.getTopRatedPagingSource()
    }

    override suspend fun getItemsCount(cache: Cache): Int {
        return cache.getTopRatedMoviesCount()
    }

    override fun setupProgress(binding: FragmentMoviesBinding, b: Boolean) {

        binding.progressBar3.isVisible = b
    }
}
