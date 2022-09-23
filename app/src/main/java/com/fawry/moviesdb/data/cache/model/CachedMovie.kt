package com.fawry.moviesdb.data.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fawry.moviesdb.domain.model.Movie

@Entity(tableName = "movies")
data class CachedMovie(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = 0,
    val movieId: Long,
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
    val backdropPath: String,
    var isPopular: Boolean = false,
    var isTopRated: Boolean = false,
    var isUpcoming: Boolean = false,
) {
    companion object {
        fun fromDomain(movie: Movie): CachedMovie {
            return CachedMovie(
                null,
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

fun CachedMovie.toDomain(): Movie {

    return Movie(
        movieId,
        originalLanguage,
        originalTitle,
        overview,
        popularity,
        posterPath,
        releaseDate,
        title,
        video,
        voteAverage,
        voteCount,
        adult,
        backdropPath
    )
}