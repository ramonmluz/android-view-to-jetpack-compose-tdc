package br.com.ramonmluz.moviehub.domain.repository

import br.com.ramonmluz.moviehub.data.model.MovieResponse
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
     fun getPopularMovies(apiKey:String, page: Int): Flow<MovieResponse>
     fun getTopRatedMovies(page: Int): Flow<MovieResponse>
     fun getUpcomingMovies(page: Int): Flow<MovieResponse>
}