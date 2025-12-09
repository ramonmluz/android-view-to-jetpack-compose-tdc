package br.com.ramonmluz.moviehub.presentation.ui.state

import br.com.ramonmluz.moviehub.data.model.MovieResponse

data class MovieState(
    val isLoading: Boolean = false,
    val movieResponse: MovieResponse? = null,
    val error: Throwable? = null
)