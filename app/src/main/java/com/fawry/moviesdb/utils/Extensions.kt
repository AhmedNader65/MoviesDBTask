package com.fawry.moviesdb.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.fawry.moviesdb.R
import com.fawry.moviesdb.ui.model.MovieDetailsUI
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

inline fun CoroutineScope.createExceptionHandler(
    message: String,
    crossinline action: (throwable: Throwable) -> Unit
) = CoroutineExceptionHandler { _, throwable ->
    Logger.e(throwable, message)
    throwable.printStackTrace()

    launch {
        action(throwable)
    }
}

@BindingAdapter("app:setupImage")
fun bindImageView(imageView: ImageView, image: String?) {
    Glide.with(imageView.context)
        .load(image?.ifEmpty { null })
        .error(R.drawable.placeholder)
        .centerCrop()
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(imageView)
}

@BindingAdapter("app:setMovieDetails")
fun bindMovieDetails(textView: TextView, movieDetailsUI: MovieDetailsUI?) {
    movieDetailsUI?.let { details ->
        var text = details.releaseDate + " • "
        text += details.voteAverage.toString() + " (${details.voteCount})"
        if (details.adult)
            text += " • Adult"
        textView.text = text
    }
}
