package com.fawry.moviesdb.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fawry.moviesdb.domain.model.NetworkException
import com.fawry.moviesdb.domain.model.NetworkUnavailableException
import com.fawry.moviesdb.domain.model.NoMoreMoviesException
import com.fawry.moviesdb.domain.model.Pagination
import com.fawry.moviesdb.domain.model.category.Category
import com.fawry.moviesdb.domain.model.category.PopularCategory
import com.fawry.moviesdb.domain.model.category.TopRatedCategory
import com.fawry.moviesdb.domain.model.category.UpcomingCategory
import com.fawry.moviesdb.domain.usecases.GetMovies
import com.fawry.moviesdb.domain.usecases.RequestNextPageOfMovies
import com.fawry.moviesdb.ui.Event
import com.fawry.moviesdb.ui.model.MovieUI
import com.fawry.moviesdb.utils.Logger
import com.fawry.moviesdb.utils.createExceptionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getMovies: GetMovies,
    private val requestNextPageOfMovies: RequestNextPageOfMovies
) : ViewModel() {

    init {
        subscribeToMoviesUpdates(PopularCategory())
        subscribeToMoviesUpdates(UpcomingCategory())
        subscribeToMoviesUpdates(TopRatedCategory())
    }

    private var currentPopularPage = 0
    private var currentTopRatedPage = 0
    private var currentUpcomingPage = 0

    var isLoadingMoreMovies: Boolean = false
        private set

    private val _popularState = MutableStateFlow(MoviesViewState())
    val popular: StateFlow<MoviesViewState> =
        _popularState.asStateFlow()

    private val _topRatedState = MutableStateFlow(MoviesViewState())
    val topRatedState: StateFlow<MoviesViewState> =
        _topRatedState.asStateFlow()

    private val _upcomingState = MutableStateFlow(MoviesViewState())
    val upcomingState: StateFlow<MoviesViewState> =
        _upcomingState.asStateFlow()


    fun onEvent(event: MoviesEvent) {
        when (event) {
            is MoviesEvent.RequestInitialMoviesList -> loadMovies(event.category)
            is MoviesEvent.RequestMoreMovies -> loadNextPageMovies(event.category)
        }
    }

    private fun subscribeToMoviesUpdates(category: Category) {
        viewModelScope.launch {

            getMovies(category = category)
                .map { movies -> movies.map {
                    MovieUI.fromDomain(it)
                } }
                .catch { cause ->
                    onFailure(cause)
                }
                .collect {
                    onNewMoviesList(category, it)
                }

        }
    }

    private fun onFailure(cause: Throwable) {
        when (cause) {
            is NetworkException,
            is NetworkUnavailableException -> {
                _popularState.update { oldState ->
                    oldState.copy(loading = false, failure = Event(cause))
                }
            }
            is NoMoreMoviesException -> {
                _popularState.update { oldState ->
                    oldState.copy(noMoreMovies = true, failure = Event(cause))
                }
            }
        }
    }

    private fun onNewMoviesList(category: Category, newMoviesList: List<MovieUI>) {
        Logger.d("Got more Movies")
        when (category) {
            is PopularCategory -> {
                val updatedMoviesList = (popular.value.movies + newMoviesList).toSet()
                _popularState.update { oldState ->
                    oldState.copy(loading = false, movies = updatedMoviesList.toList())
                }
            }
            is TopRatedCategory -> {

                val updatedMoviesList = (topRatedState.value.movies + newMoviesList).toSet()
                _topRatedState.update { oldState ->
                    oldState.copy(loading = false, movies = updatedMoviesList.toList())
                }
            }
            else -> {

                val updatedMoviesList = (upcomingState.value.movies + newMoviesList).toSet()
                _upcomingState.update { oldState ->
                    oldState.copy(loading = false, movies = updatedMoviesList.toList())
                }
            }
        }
    }

    private fun loadNextPageMovies(category: Category) {
        isLoadingMoreMovies = true
        val errorMessage = "Failed to fetch Movies"
        val exceptionHandler = viewModelScope.createExceptionHandler(errorMessage) { onFailure(it) }
        viewModelScope.launch(exceptionHandler + Dispatchers.IO) {
            Logger.d("Requesting more movies.")
            val pagination = when (category) {
                is PopularCategory -> requestNextPageOfMovies(category, ++currentPopularPage)
                is TopRatedCategory -> requestNextPageOfMovies(category, ++currentTopRatedPage)
                else -> requestNextPageOfMovies(category, ++currentUpcomingPage)
            }

            onPaginationInfoObtained(category, pagination)
            isLoadingMoreMovies = false
        }
    }

    private fun onPaginationInfoObtained(category: Category, pagination: Pagination) {

        when (category) {
            is PopularCategory -> currentPopularPage = pagination.currentPage
            is TopRatedCategory -> currentTopRatedPage = pagination.currentPage
            else -> currentUpcomingPage = pagination.currentPage
        }
    }

    private fun loadMovies(category: Category) {
        if (popular.value.movies.isEmpty()) {
            loadNextPageMovies(category)
        }
    }

    fun isLastPage(category: Category): Boolean {
        return when (category) {
            is PopularCategory -> popular.value.noMoreMovies
            is TopRatedCategory -> topRatedState.value.noMoreMovies
            else -> upcomingState.value.noMoreMovies
        }
    }
}
