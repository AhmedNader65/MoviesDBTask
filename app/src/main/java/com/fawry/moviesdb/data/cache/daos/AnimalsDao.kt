package com.fawry.moviesdb.data.cache.daos

import androidx.room.*
import com.fawry.moviesdb.data.cache.model.CachedMovie
import kotlinx.coroutines.flow.Flow

@Dao
abstract class MoviesDao {

    @Transaction
    @Query("SELECT * FROM movies ORDER BY movieId DESC")
    abstract fun getAllMovies(): Flow<List<CachedMovie>>

    @Query("SELECT * FROM movies WHERE movieId = :id")
    abstract suspend fun getMovieById(id: Long): CachedMovie

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertMovie(vararg movie: CachedMovie)

}
