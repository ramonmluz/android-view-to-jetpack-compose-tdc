package br.com.ramonmluz.moviehub.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.ramonmluz.moviehub.data.model.MovieResponse
import br.com.ramonmluz.moviehub.domain.business.MovieBusiness
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MovieViewModel(private val movieBusiness: MovieBusiness) : ViewModel() {

    fun loadPopularMovies() {
        viewModelScope.launch {
            movieBusiness.getPopularMovies(String(), 1)
                .catch {}
                .collect(::onStatusSuccess)
        }
    }

    private fun onStatusSuccess(movieResponse: MovieResponse) {

    }
}