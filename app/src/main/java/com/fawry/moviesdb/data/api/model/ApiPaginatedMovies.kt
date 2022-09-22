package com.fawry.moviesdb.data.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiPaginatedMovies(
    @field:Json(name = "results") val movies: List<ApiMovies>?,
    @field:Json(name = "total_results") val totalCount: Int?,
    @field:Json(name = "page") val currentPage: Int?,
    @field:Json(name = "total_pages") val totalPages: Int?
)

