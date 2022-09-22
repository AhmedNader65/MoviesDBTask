package com.fawry.moviesdb.data.api

import com.fawry.moviesdb.BuildConfig
import com.fawry.moviesdb.data.api.model.ApiPaginatedMovies
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET(ApiConstants.POPULAR_ENDPOINT)
    suspend fun getPopularMovies(
        @Query(ApiParameters.PAGE) pageToLoad: Int,
        @Query(ApiParameters.API_KEY) apiKey: String = BuildConfig.API_KEY
    ): ApiPaginatedMovies

    @GET(ApiConstants.TOP_RATED_ENDPOINT)
    suspend fun getTopRatedMovies(
        @Query(ApiParameters.PAGE) pageToLoad: Int,
        @Query(ApiParameters.API_KEY) apiKey: String = BuildConfig.API_KEY
    ): ApiPaginatedMovies

    @GET(ApiConstants.UP_COMING_ENDPOINT)
    suspend fun getUpComingMovies(
        @Query(ApiParameters.PAGE) pageToLoad: Int,
        @Query(ApiParameters.API_KEY) apiKey: String = BuildConfig.API_KEY
    ): ApiPaginatedMovies

}