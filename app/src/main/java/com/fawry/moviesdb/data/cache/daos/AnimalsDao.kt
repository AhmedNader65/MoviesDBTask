package com.fawry.moviesdb.data.cache.daos

import androidx.room.*
import com.fawry.moviesdb.data.cache.model.CachedMovie
import io.reactivex.Flowable

@Dao
abstract class MoviesDao {

    @Transaction
    @Query("SELECT * FROM movies ORDER BY movieId DESC")
    abstract fun getAllMovies(): Flowable<List<CachedMovie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertMovie(vararg movie: CachedMovie)

}
