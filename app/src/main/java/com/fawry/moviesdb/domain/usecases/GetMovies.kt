package com.fawry.moviesdb.domain.usecases

import com.fawry.moviesdb.domain.model.category.Category
import com.fawry.moviesdb.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.filter
import javax.inject.Inject

class GetMovies @Inject constructor(private val moviesRepository: MoviesRepository) {

    operator fun invoke(category: Category) = moviesRepository.getMovies(category)
        .filter { it.isNotEmpty() }
}
