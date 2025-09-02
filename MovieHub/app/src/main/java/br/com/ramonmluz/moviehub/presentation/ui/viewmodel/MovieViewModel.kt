package br.com.ramonmluz.moviehub.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.ramonmluz.moviehub.data.model.MovieResponse
import br.com.ramonmluz.moviehub.domain.business.MovieBusiness
import br.com.ramonmluz.moviehub.presentation.ui.state.MovieState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieViewModel(private val movieBusiness: MovieBusiness) : ViewModel() {

    private val _movieState = MutableStateFlow(MovieState())
    val movieState = _movieState.asStateFlow()

    init {
        loadPopularMovies()
    }

    fun loadPopularMovies(page: Int = 1) {
        viewModelScope.launch {
            movieBusiness.getPopularMovies(String(), page)
                .onStart {
                    onLoading(isLoading = true)
                    onError(null)
                }
                .onCompletion {
                    onLoading(isLoading = false)
                    onError(null)
                }
                .catch { exception ->
                    onError(exception)
                }
                .collect(::onSuccess)
        }
    }

    private fun onLoading(isLoading: Boolean) {
        _movieState.update { state ->
            state.copy(
                isLoading = isLoading
            )
        }
    }

    private fun onSuccess(movieResponse: MovieResponse) {
        _movieState.update { state ->
            state.copy(
                movieResponse = movieResponse,
                isLoading = false
            )
        }
    }

    private fun onError(exception: Throwable?) {
        _movieState.update { state ->
            state.copy(
                error = exception
            )
        }
    }
}
