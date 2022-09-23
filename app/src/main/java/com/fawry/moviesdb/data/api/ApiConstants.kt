package com.fawry.moviesdb.data.api

object ApiConstants {
  const val BASE_ENDPOINT = "https://api.themoviedb.org/3/"
  const val POPULAR_ENDPOINT = "movie/popular"
  const val TOP_RATED_ENDPOINT = "movie/top_rated"
  const val UP_COMING_ENDPOINT = "movie/upcoming"
  const val IMAGES_BASE = "https://www.themoviedb.org/t/p/w220_and_h330_face"
  const val COVERS_BASE = "https://image.tmdb.org/t/p/w1920_and_h800_multi_faces"
}

object ApiParameters {
  const val API_KEY = "api_key"
  // MOVIE
  const val PAGE = "page"
  const val PAGE_SIZE = 20
}