package br.com.ramonmluz.moviehub.network.provider

import br.com.ramonmluz.moviehub.network.api.MovieApiService
import br.com.ramonmluz.moviehub.network.okhttp.interceptor.OkhttpFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

object RetrofitProvider {
    fun provideRetrofit(okhttpFactory: OkhttpFactory): Retrofit {
        val client = okhttpFactory.getClientRetrofit()
        return Retrofit.Builder()
            .baseUrl(MovieApiService.BASE_URL)
            .client(client)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }
}
