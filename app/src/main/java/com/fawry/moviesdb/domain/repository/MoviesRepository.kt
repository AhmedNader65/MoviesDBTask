package com.fawry.moviesdb.domain.repository

import com.fawry.moviesdb.domain.model.Movie
import com.fawry.moviesdb.domain.model.PaginatedMovies
import com.fawry.moviesdb.domain.model.category.Category
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    fun getMovies(category: Category): Flow<List<Movie>>

    suspend fun getMovieById(id: Long): Movie

    suspend fun requestMoreMovies(
        category: Category,
        pageToLoad: Int
    ): PaginatedMovies

    suspend fun storeMovies(category: Category,movies: List<Movie>)
}