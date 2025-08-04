package br.com.ramonmluz.moviehub.data.model

import br.com.ramonmluz.moviehub.data.model.Movie

data class SearchMoviesResponse(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)