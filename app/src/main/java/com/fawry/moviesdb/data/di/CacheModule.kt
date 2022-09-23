package com.fawry.moviesdb.data.di

import android.content.Context
import androidx.room.Room
import com.fawry.moviesdb.data.cache.Cache
import com.fawry.moviesdb.data.cache.MoviesDatabase
import com.fawry.moviesdb.data.cache.RoomCache
import com.fawry.moviesdb.data.cache.daos.MoviesDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CacheModule {

    @Binds
    abstract fun bindCache(cache: RoomCache): Cache

    companion object {

        @Provides
        @Singleton
        fun provideDatabase(
            @ApplicationContext context: Context
        ): MoviesDatabase {
            return Room.databaseBuilder(
                context,
                MoviesDatabase::class.java,
                "movies.db"
            )
                .fallbackToDestructiveMigration()
                .build()
        }

        @Provides
        fun provideMoviesDao(
            petSaveDatabase: MoviesDatabase
        ): MoviesDao = petSaveDatabase.moviesDao()
    }
}
