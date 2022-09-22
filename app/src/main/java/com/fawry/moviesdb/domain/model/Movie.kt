package com.fawry.moviesdb.domain.model

import com.squareup.moshi.Json

class Movie(
    val id: Long,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val voteCount: Int,
    val adult: Boolean,
    val backdropPath: String,
    val genreIds: ArrayList<Long>
) {
}