package com.fawry.moviesdb.ui.model

import com.fawry.moviesdb.domain.model.Movie

data class MovieDetailsUI(
    val id: Long,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
    val adult: Boolean,
    val backdropPath: String
) {
    companion object {
        fun fromDomain(movie: Movie): MovieDetailsUI {
            return MovieDetailsUI(
                movie.id,
                movie.originalLanguage,
                movie.originalTitle,
                movie.overview,
                movie.popularity,
                movie.posterPath,
                movie.releaseDate,
                movie.title,
                movie.video,
                movie.voteAverage,
                movie.voteCount,
                movie.adult,
                movie.backdropPath
            )
        }
    }
}
