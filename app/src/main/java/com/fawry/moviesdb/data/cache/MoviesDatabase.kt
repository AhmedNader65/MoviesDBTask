package com.fawry.moviesdb.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fawry.moviesdb.data.cache.daos.MoviesDao
import com.fawry.moviesdb.data.cache.model.CachedMovie

@Database(
    entities = [
        CachedMovie::class
    ],
    version = 1
)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}
