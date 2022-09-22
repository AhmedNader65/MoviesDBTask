package com.fawry.moviesdb.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fawry.moviesdb.domain.model.NetworkException
import com.fawry.moviesdb.domain.model.NetworkUnavailableException
import com.fawry.moviesdb.domain.model.NoMoreMoviesException
import com.fawry.moviesdb.domain.model.Pagination
import com.fawry.moviesdb.domain.usecases.GetMovies
import com.fawry.moviesdb.domain.usecases.RequestNextPageOfMovies
import com.fawry.moviesdb.ui.Event
import com.fawry.moviesdb.ui.model.MovieUI
import com.fawry.moviesdb.utils.Logger
import com.fawry.moviesdb.utils.createExceptionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getMovies: GetMovies,
    private val requestNextPageOfMovies: RequestNextPageOfMovies
) : ViewModel() {

    init {
        subscribeToMoviesUpdates()
    }

    private var currentPage = 0

    val isLastPage: Boolean
        get() = state.value.noMoreMovies

    var isLoadingMoreMovies: Boolean = false
        private set

    private val _state = MutableStateFlow(MoviesViewState())
    val state: StateFlow<MoviesViewState> =
        _state.asStateFlow()


    fun onEvent(event: MoviesEvent) {
        when (event) {
            is MoviesEvent.RequestInitialMoviesList -> loadMovies()
            is MoviesEvent.RequestMoreMovies -> loadNextPageMovies()
        }
    }

    private fun subscribeToMoviesUpdates() {
        viewModelScope.launch {

            getMovies()
                .map { movies -> movies.map { MovieUI.fromDomain(it) } }
                .catch { cause ->
                    onFailure(cause)
                }
                .collect {
                    onNewMoviesList(it)
                }

        }
    }

    private fun onFailure(cause: Throwable) {
        when (cause) {
            is NetworkException,
            is NetworkUnavailableException -> {
                _state.update { oldState ->
                    oldState.copy(loading = false, failure = Event(cause))
                }
            }
            is NoMoreMoviesException -> {
                _state.update { oldState ->
                    oldState.copy(noMoreMovies = true, failure = Event(cause))
                }
            }
        }
    }

    private fun onNewMoviesList(newMoviesList: List<MovieUI>) {
        Logger.d("Got more Movies")
        val updatedMoviesList = (state.value.movies + newMoviesList).toSet()
        _state.update { oldState ->
            oldState.copy(loading = false, movies = updatedMoviesList.toList())
        }
    }

    private fun loadNextPageMovies() {
        isLoadingMoreMovies = true
        val errorMessage = "Failed to fetch Movies"
        val exceptionHandler = viewModelScope.createExceptionHandler(errorMessage) { onFailure(it) }
        viewModelScope.launch(exceptionHandler) {
            Logger.d("Requesting more movies.")
            val pagination = requestNextPageOfMovies(++currentPage)
            onPaginationInfoObtained(pagination)
            isLoadingMoreMovies = false
        }
    }

    private fun onPaginationInfoObtained(pagination: Pagination) {
        currentPage = pagination.currentPage
    }

    private fun loadMovies() {
        if (state.value.movies.isEmpty()) {
            loadNextPageMovies()
        }
    }
}
