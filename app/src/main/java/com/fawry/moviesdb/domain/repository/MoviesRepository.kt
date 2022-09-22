package com.fawry.moviesdb.domain.repository

import com.fawry.moviesdb.domain.model.Movie
import com.fawry.moviesdb.domain.model.PaginatedMovies
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    fun getMovies(): Flow<List<Movie>>

    suspend fun requestMoreMovies(
        pageToLoad: Int
    ): PaginatedMovies

    suspend fun storeMovies(movies: List<Movie>)
}