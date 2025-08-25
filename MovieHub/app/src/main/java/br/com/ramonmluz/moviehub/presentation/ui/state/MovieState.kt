package br.com.ramonmluz.moviehub.presentation.ui.state

import br.com.ramonmluz.moviehub.data.model.MovieResponse

sealed interface MovieState {
    object Initial : MovieState
    object Loading : MovieState
    data class Success(val data: MovieResponse) : MovieState
    data class Error(val error: Throwable) : MovieState
}