package br.com.ramonmluz.moviehub.data.model

import kotlinx.serialization.SerialName

data class MovieResponse(
    val page: Int,
    val results: List<Movie>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)