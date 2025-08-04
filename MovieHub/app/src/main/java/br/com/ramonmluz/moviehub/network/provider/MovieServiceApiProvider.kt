package br.com.ramonmluz.moviehub.network.provider

import br.com.ramonmluz.moviehub.network.api.MovieApiService
import retrofit2.Retrofit

object MovieServiceApiProvider {
    fun providerMovieServiceApi(retrofit: Retrofit): MovieApiService =
        retrofit.create(MovieApiService::class.java)
}