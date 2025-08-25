package br.com.ramonmluz.moviehub.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.ramonmluz.moviehub.data.model.MovieResponse
import br.com.ramonmluz.moviehub.domain.business.MovieBusiness
import br.com.ramonmluz.moviehub.presentation.ui.state.MovieState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MovieViewModel(private val movieBusiness: MovieBusiness) : ViewModel() {

    private val _movieState = MutableStateFlow<MovieState>(MovieState.Initial)
    val movieState = _movieState.asStateFlow()

    init {
        loadPopularMovies()
    }

    fun loadPopularMovies() {
        viewModelScope.launch {
            movieBusiness.getPopularMovies(String(), 1)
                .onStart { _movieState.value = MovieState.Loading }
                .catch { exception ->
                   _movieState.value = MovieState.Error(exception)
                }
                .collect(::onStatusSuccess)
        }
    }

    private fun onStatusSuccess(movieResponse: MovieResponse) {
        _movieState.value = MovieState.Success(movieResponse)
    }
}