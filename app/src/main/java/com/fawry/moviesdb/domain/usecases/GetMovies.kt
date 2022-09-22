package com.fawry.moviesdb.domain.usecases

import com.fawry.moviesdb.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.filter
import javax.inject.Inject

class GetMovies @Inject constructor(private val moviesRepository: MoviesRepository) {

    operator fun invoke() = moviesRepository.getMovies()
        .filter { it.isNotEmpty() }
}
