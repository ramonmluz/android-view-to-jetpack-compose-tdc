package br.com.ramonmluz.moviehub.network.provider

import br.com.ramonmluz.moviehub.network.api.MovieApi
import br.com.ramonmluz.moviehub.network.okhttp.interceptor.OkhttpFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

object RetrofitProvider {
    fun provideRetrofit(okhttpFactory: OkhttpFactory): Retrofit {
        val client = okhttpFactory.getClientRetrofit()
        val contentType = "application/json".toMediaType()
        val json = Json { ignoreUnknownKeys = true } // Configure Json as needed
        return Retrofit.Builder()
            .baseUrl(MovieApi.BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }
}
