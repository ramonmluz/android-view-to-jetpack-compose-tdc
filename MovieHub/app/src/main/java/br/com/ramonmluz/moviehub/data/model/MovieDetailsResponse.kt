package br.com.ramonmluz.moviehub.data.model

import br.com.ramonmluz.moviehub.data.model.Genre

data class MovieDetailsResponse(
    val id: Int,
    val title: String,
    val overview: String,
    val release_date: String,
    val poster_path: String?,
    val genres: List<Genre>
)