package com.fawry.moviesdb.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fawry.moviesdb.domain.usecases.GetMovies
import com.fawry.moviesdb.domain.usecases.RequestNextPageOfMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getMovies: GetMovies,
    private val requestNextPageOfMovies: RequestNextPageOfMovies
): ViewModel() {
    fun getMovies() {
        val x = 5

        viewModelScope.launch() {
            requestNextPageOfMovies(1)
        }
    }
}
