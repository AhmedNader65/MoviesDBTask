package com.fawry.moviesdb.domain.usecases

import com.fawry.moviesdb.domain.model.NoMoreMoviesException
import com.fawry.moviesdb.domain.model.Pagination
import com.fawry.moviesdb.domain.model.category.Category
import com.fawry.moviesdb.domain.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RequestNextPageOfMovies @Inject constructor(private val moviesRepository: MoviesRepository) {

    suspend operator fun invoke(
        category: Category,
        pageToLoad: Int
    ): Pagination {
        return withContext(Dispatchers.IO) {
            val (movies, pagination) = moviesRepository.requestMoreMovies(category, pageToLoad)
            if (movies.isEmpty())
                throw NoMoreMoviesException("No more movies")
            moviesRepository.storeMovies(category,movies)
            return@withContext pagination
        }
    }
}
