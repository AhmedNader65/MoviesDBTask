package com.fawry.moviesdb.data.api.model

import com.fawry.moviesdb.domain.model.PaginatedMovies
import com.fawry.moviesdb.domain.model.Pagination
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiPaginatedMovies(
    @field:Json(name = "results") val movies: List<ApiMovies>,
    @field:Json(name = "total_results") val totalCount: Int,
    @field:Json(name = "page") val currentPage: Int,
    @field:Json(name = "total_pages") val totalPages: Int
)

fun ApiPaginatedMovies.mapToDomain(): PaginatedMovies {
    return PaginatedMovies(movies.map { it.mapToDomain() }, Pagination(currentPage, totalPages))
}