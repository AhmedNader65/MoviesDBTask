package com.fawry.moviesdb.data.api.model

import com.fawry.moviesdb.data.api.ApiConstants
import com.fawry.moviesdb.domain.model.Movie
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiMovies(
    @field:Json(name = "id") val id: Long?,
    @field:Json(name = "original_language") val originalLanguage: String?,
    @field:Json(name = "original_title") val originalTitle: String?,
    @field:Json(name = "overview") val overview: String?,
    @field:Json(name = "popularity") val popularity: Double?,
    @field:Json(name = "poster_path") val posterPath: String?,
    @field:Json(name = "release_date") val releaseDate: String?,
    @field:Json(name = "title") val title: String?,
    @field:Json(name = "video") val video: Boolean?,
    @field:Json(name = "vote_average") val voteAverage: Double?,
    @field:Json(name = "vote_count") val voteCount: Int?,
    @field:Json(name = "adult") val adult: Boolean?,
    @field:Json(name = "backdrop_path") val backdropPath: String?,
    @field:Json(name = "genre_ids") val genreIds: List<Long>?,
)

fun ApiMovies.mapToDomain(): Movie {
    return Movie(
        id ?: throw MappingException("Movie ID cannot be null"),
        originalLanguage.orEmpty(),
        originalTitle.orEmpty(),
        overview.orEmpty(),
        popularity ?: 0.0,
        ApiConstants.IMAGES_BASE + posterPath.orEmpty(),
        releaseDate.orEmpty(),
        title.orEmpty(),
        video ?: false,
        voteAverage ?: 0.0,
        voteCount ?: 0,
        adult ?: false,
        ApiConstants.IMAGES_BASE + backdropPath.orEmpty()
    )
}