package br.com.ramonmluz.moviehub.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConfigurationResponse(
    val images: ImagesConfiguration,
    @SerialName("change_keys")
    val changeKeys: List<String>
)