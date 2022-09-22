package com.fawry.moviesdb.domain.repository

import com.fawry.moviesdb.domain.model.PaginatedMovies

interface MoviesRepository {
    suspend fun requestMoreMovies(
        pageToLoad: Int
    ): PaginatedMovies

}