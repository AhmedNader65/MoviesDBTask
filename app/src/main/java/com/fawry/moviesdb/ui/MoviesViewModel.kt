package com.fawry.moviesdb.ui

import androidx.lifecycle.ViewModel
import com.fawry.moviesdb.domain.usecases.GetMovies
import com.fawry.moviesdb.domain.usecases.RequestNextPageOfMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getMovies: GetMovies,
    private val requestNextPageOfMovies: RequestNextPageOfMovies
): ViewModel() {
    init {
        getMovies()
    }
}
