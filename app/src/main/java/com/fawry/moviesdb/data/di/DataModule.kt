package com.fawry.moviesdb.data.di

import com.fawry.moviesdb.data.MoviesRepositoryImp
import com.fawry.moviesdb.domain.repository.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class DataModule {

    @Binds
    @ActivityRetainedScoped
    abstract fun bindMoviesRepository(repository: MoviesRepositoryImp): MoviesRepository
}
