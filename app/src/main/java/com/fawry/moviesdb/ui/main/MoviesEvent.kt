package com.fawry.moviesdb.ui.main


sealed class MoviesEvent {
    object RequestInitialMoviesList: MoviesEvent()
    object RequestMoreMovies: MoviesEvent()
}