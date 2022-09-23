package com.fawry.moviesdb.domain.repository

import androidx.paging.PagingData
import com.fawry.moviesdb.domain.model.Movie
import com.fawry.moviesdb.domain.model.category.Category
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    suspend fun getMovies(category: Category): Flow<PagingData<Movie>>

    suspend fun getMovieById(id: Long): Movie
}
