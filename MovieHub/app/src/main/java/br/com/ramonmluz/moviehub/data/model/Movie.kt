package br.com.ramonmluz.moviehub.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    @SerialName("id") val id:Long,
    @SerialName("overview") val overview:String,
    @SerialName("poster_path") val posterPath:String?,
    @SerialName("original_title") val originalTitle:String,
    @SerialName("vote_average") val voteAverage:String,
    @SerialName("release_date") val releaseDate:String,
    @SerialName("backdrop_path") val backdropPath:String?
)