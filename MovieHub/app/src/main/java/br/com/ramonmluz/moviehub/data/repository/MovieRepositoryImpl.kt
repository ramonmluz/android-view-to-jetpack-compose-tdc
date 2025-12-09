package br.com.ramonmluz.moviehub.data.repository

import br.com.ramonmluz.moviehub.data.model.MovieResponse
import br.com.ramonmluz.moviehub.domain.repository.MovieRepository
import br.com.ramonmluz.moviehub.network.api.MovieApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieRepositoryImpl(private val api: MovieApi) : MovieRepository {
    override fun getPopularMovies(apiKey: String, page: Int): Flow<MovieResponse> =
        flow {
            emit(api.getPopularMovies(page))
        }

    override fun getTopRatedMovies(page: Int): Flow<MovieResponse> {
        TODO("Not yet implemented")
    }

    override fun getUpcomingMovies(page: Int): Flow<MovieResponse> {
        TODO("Not yet implemented")
    }
}