package com.fawry.moviesdb.data

import com.fawry.moviesdb.data.api.MoviesApi
import com.fawry.moviesdb.data.api.model.mapToDomain
import com.fawry.moviesdb.domain.model.NetworkException
import com.fawry.moviesdb.domain.model.PaginatedMovies
import com.fawry.moviesdb.domain.repository.MoviesRepository
import retrofit2.HttpException
import javax.inject.Inject

class MoviesRepositoryImp @Inject constructor(
    private val api: MoviesApi
) : MoviesRepository {
    override suspend fun requestMoreMovies(
        pageToLoad: Int
    ): PaginatedMovies {
        try {
            val results = api.getPopularMovies(pageToLoad)
            return results.mapToDomain()
        } catch (exception: HttpException) {
            throw NetworkException(exception.message ?: "Code ${exception.code()}")
        }
    }
}