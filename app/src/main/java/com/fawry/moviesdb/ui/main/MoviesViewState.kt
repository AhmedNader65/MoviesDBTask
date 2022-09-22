package com.fawry.moviesdb.ui.main

import com.fawry.moviesdb.ui.Event
import com.fawry.moviesdb.ui.model.MovieUI

data class MoviesViewState(
    val loading: Boolean = true,
    val movies: List<MovieUI> = emptyList(),
    val noMoreMovies: Boolean = false,
    val failure: Event<Throwable>? = null
)