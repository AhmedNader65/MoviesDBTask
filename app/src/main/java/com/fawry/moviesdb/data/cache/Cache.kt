package com.fawry.moviesdb.data.cache

import androidx.paging.PagingSource
import com.fawry.moviesdb.data.cache.model.CachedMovie

interface Cache {

    fun getPopularPagingSource(): PagingSource<Int, CachedMovie>
    fun getUpcomingPagingSource(): PagingSource<Int, CachedMovie>
    fun getTopRatedPagingSource(): PagingSource<Int, CachedMovie>

    suspend fun getPopularMoviesCount(): Int
    suspend fun getTopRatedMoviesCount(): Int
    suspend fun getUpcomingMoviesCount(): Int

    suspend fun getMovieById(id: Long): CachedMovie?

    suspend fun getCreatedAt(): Long?

    suspend fun storeMovies(vararg movie: CachedMovie)
    suspend fun deleteAll()
}
