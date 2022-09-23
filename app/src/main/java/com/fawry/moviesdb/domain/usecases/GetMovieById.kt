package com.fawry.moviesdb.domain.usecases

import com.fawry.moviesdb.domain.repository.MoviesRepository
import javax.inject.Inject

class GetMovieById @Inject constructor(private val moviesRepository: MoviesRepository) {

    suspend operator fun invoke(movieId: Long) = moviesRepository.getMovieById(movieId)
}
