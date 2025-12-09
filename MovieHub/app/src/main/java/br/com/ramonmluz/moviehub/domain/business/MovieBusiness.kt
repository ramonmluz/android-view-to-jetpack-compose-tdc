package br.com.ramonmluz.moviehub.domain.business

import br.com.ramonmluz.moviehub.data.model.MovieResponse
import br.com.ramonmluz.moviehub.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class MovieBusiness(private val movieRepository: MovieRepository) {
    fun getPopularMovies(apiKey: String, page: Int): Flow<MovieResponse> {
        return movieRepository.getPopularMovies(apiKey, page)
    }
}