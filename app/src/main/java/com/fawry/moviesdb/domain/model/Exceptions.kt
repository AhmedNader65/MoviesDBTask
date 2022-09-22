package com.fawry.moviesdb.domain.model

import java.io.IOException


class NoMoreMoviesException(message: String): Exception(message)

class NetworkUnavailableException(message: String = "No network available :(") : IOException(message)

class NetworkException(message: String): Exception(message)
