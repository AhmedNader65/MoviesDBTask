package com.fawry.moviesdb.ui.main

import com.fawry.moviesdb.domain.model.category.Category


sealed class MoviesEvent {
    class RequestInitialMoviesList(val category: Category) : MoviesEvent()
    class RequestMoreMovies(val category: Category) : MoviesEvent()
}