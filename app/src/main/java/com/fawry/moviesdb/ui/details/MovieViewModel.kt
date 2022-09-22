package com.fawry.moviesdb.ui.details

import androidx.lifecycle.ViewModel
import com.fawry.moviesdb.domain.usecases.GetMovieById
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getMovieById: GetMovieById
) : ViewModel() {

}
