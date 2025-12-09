package br.com.ramonmluz.moviehub.data.model

import kotlinx.serialization.SerialName

data class MovieDetailsResponse(
    val id: Int,
    val title: String,
    val overview: String,
    @SerialName("release_date")
    val releaseDate:String,
    @SerialName("poster_path")
    val posterPath:String?,
    val genres: List<Genre>
)