package com.fawry.moviesdb.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fawry.moviesdb.domain.usecases.GetMovieById
import com.fawry.moviesdb.ui.main.MoviesViewState
import com.fawry.moviesdb.ui.model.MovieDetailsUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getMovieById: GetMovieById
) : ViewModel() {

    private val _state = MutableStateFlow(MovieDetailsViewState())
    val state: StateFlow<MovieDetailsViewState> =
        _state.asStateFlow()

    fun getMovieDetails(id: Long) {
        viewModelScope.launch {
            val movie = getMovieById(id)
            _state.update { oldState ->
                oldState.copy(loading = false, movie = MovieDetailsUI.fromDomain(movie))
            }
        }
    }
}
