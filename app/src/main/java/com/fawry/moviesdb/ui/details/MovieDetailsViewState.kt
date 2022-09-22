package com.fawry.moviesdb.ui.details

import com.fawry.moviesdb.ui.Event
import com.fawry.moviesdb.ui.model.MovieDetailsUI
import com.fawry.moviesdb.ui.model.MovieUI

data class MovieDetailsViewState(
    val loading: Boolean = true,
    val movie: MovieDetailsUI? = null,
)