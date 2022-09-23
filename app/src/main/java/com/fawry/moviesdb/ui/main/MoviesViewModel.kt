package com.fawry.moviesdb.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.fawry.moviesdb.domain.model.category.Category
import com.fawry.moviesdb.domain.model.category.PopularCategory
import com.fawry.moviesdb.domain.model.category.TopRatedCategory
import com.fawry.moviesdb.domain.model.category.UpcomingCategory
import com.fawry.moviesdb.domain.usecases.GetMovies
import com.fawry.moviesdb.ui.model.MovieUI
import com.fawry.moviesdb.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getMovies: GetMovies,
) : ViewModel() {


    private val _topRatedState = MutableStateFlow<PagingData<MovieUI>?>(null)
    val topRatedState: StateFlow<PagingData<MovieUI>?> =
        _topRatedState.asStateFlow()

    private val _upcomingState = MutableStateFlow<PagingData<MovieUI>?>(null)
    val upcomingState: StateFlow<PagingData<MovieUI>?> =
        _upcomingState.asStateFlow()

    private val _popularState = MutableStateFlow<PagingData<MovieUI>?>(null)
    val popular: StateFlow<PagingData<MovieUI>?> =
        _popularState.asStateFlow()

    init {
        viewModelScope.launch {
            subscribeToMoviesUpdates(TopRatedCategory())
            subscribeToMoviesUpdates(PopularCategory())
            subscribeToMoviesUpdates(UpcomingCategory())
        }
    }

    private fun subscribeToMoviesUpdates(category: Category) {
        viewModelScope.launch {

            getMovies(category = category).cachedIn(viewModelScope)
                .map { movies ->
                    movies.map {
                        MovieUI.fromDomain(it)
                    }
                }
                .collect {
                    onNewMoviesList(category, it)
                }

        }
    }

    private suspend fun onNewMoviesList(category: Category, newMoviesList: PagingData<MovieUI>) {
        Logger.d("Got more Movies $category")
        when (category) {
            is TopRatedCategory -> {
                _topRatedState.emit(newMoviesList)
            }
            is PopularCategory -> {
                _popularState.emit(newMoviesList)
            }
            else -> {
                _upcomingState.emit(newMoviesList)
            }
        }
    }

}

