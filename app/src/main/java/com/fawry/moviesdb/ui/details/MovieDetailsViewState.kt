package com.fawry.moviesdb.ui.details

import com.fawry.moviesdb.ui.model.MovieDetailsUI

data class MovieDetailsViewState(
    val loading: Boolean = true,
    val movie: MovieDetailsUI? = null,
)