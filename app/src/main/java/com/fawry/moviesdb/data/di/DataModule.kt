package com.fawry.moviesdb.data.di

import android.content.Context
import androidx.room.Room
import com.fawry.moviesdb.data.MoviesRepositoryImp
import com.fawry.moviesdb.data.cache.Cache
import com.fawry.moviesdb.data.cache.MoviesDatabase
import com.fawry.moviesdb.data.cache.RoomCache
import com.fawry.moviesdb.data.cache.daos.MoviesDao
import com.fawry.moviesdb.domain.repository.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class DataModule {

    @Binds
    @ActivityRetainedScoped
    abstract fun bindMoviesRepository(repository: MoviesRepositoryImp): MoviesRepository

}
