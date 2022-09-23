package com.fawry.moviesdb.data.cache.daos

import androidx.paging.PagingSource
import androidx.room.*
import com.fawry.moviesdb.data.cache.model.CachedMovie
import com.fawry.moviesdb.domain.model.category.Category
import kotlinx.coroutines.flow.Flow

@Dao
abstract class MoviesDao {


    @Query("SELECT COUNT(movieId) FROM movies where isPopular = 1")
    abstract suspend fun getPopularMoviesCount(): Int

    @Query("SELECT COUNT(movieId) FROM movies where isTopRated = 1")
    abstract suspend fun getTopRatedMoviesCount(): Int

    @Query("SELECT COUNT(movieId) FROM movies where isUpcoming = 1")
    abstract suspend fun getUpcomingMoviesCount(): Int

    @Query("SELECT * FROM movies where isPopular = 1")
    abstract fun popularPagingSource(): PagingSource<Int, CachedMovie>

    @Query("SELECT * FROM movies where isTopRated = 1 ")
    abstract fun topRatedPagingSource(): PagingSource<Int, CachedMovie>

    @Query("SELECT * FROM movies where isUpcoming = 1")
    abstract fun upcomingPagingSource(): PagingSource<Int, CachedMovie>

    @Query("SELECT * FROM movies WHERE movieId = :id")
    abstract suspend fun getMovieById(id: Long): CachedMovie?

    @Query("SELECT createdAt FROM movies order by id ASC LIMIT 1")
    abstract suspend fun getCreatedAt() : Long?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertMovie(vararg movie: CachedMovie)

    @Query("DELETE FROM movies ")
    abstract suspend fun deleteAll()

}
