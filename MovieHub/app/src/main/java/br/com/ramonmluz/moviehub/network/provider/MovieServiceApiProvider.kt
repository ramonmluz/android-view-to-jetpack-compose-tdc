package br.com.ramonmluz.moviehub.network.provider

import br.com.ramonmluz.moviehub.network.api.MovieApi
import retrofit2.Retrofit

object MovieServiceApiProvider {
    fun providerMovieServiceApi(retrofit: Retrofit): MovieApi =
        retrofit.create(MovieApi::class.java)
}