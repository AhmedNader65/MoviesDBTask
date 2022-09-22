package com.fawry.moviesdb.data.cache.daos

import androidx.room.*
import com.fawry.moviesdb.data.cache.model.CachedMovie
import com.fawry.moviesdb.domain.model.category.Category
import kotlinx.coroutines.flow.Flow

@Dao
abstract class MoviesDao {

    @Transaction
    @Query("SELECT * FROM movies where isPopular = 1 ORDER BY movieId DESC ")
    abstract fun getPopularMovies(): Flow<List<CachedMovie>>

    @Transaction
    @Query("SELECT * FROM movies where isTopRated = 1 ORDER BY movieId DESC ")
    abstract fun getTopRatedMovies(): Flow<List<CachedMovie>>

    @Transaction
    @Query("SELECT * FROM movies where isUpcoming = 1 ORDER BY movieId DESC ")
    abstract fun getUpcomingMovies(): Flow<List<CachedMovie>>

    @Query("SELECT * FROM movies WHERE movieId = :id")
    abstract suspend fun getMovieById(id: Long): CachedMovie?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertMovie(vararg movie: CachedMovie)


    suspend fun insertOrUpdateMovie(category: Category, vararg movie: CachedMovie) {
        movie.forEach {
            val itemFromDB = getMovieById(it.movieId)
            if (itemFromDB == null) {
                val updated = category.setCacheCategoryValue(it)
                insertMovie(updated)
            } else {
                insertMovie(itemFromDB)
            }
        }
    }

}
