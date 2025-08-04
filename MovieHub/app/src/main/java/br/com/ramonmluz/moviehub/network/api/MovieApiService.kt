package br.com.ramonmluz.moviehub.network.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/" // ex: w500, original
    }

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
        @Query("language") language: String = "pt-BR", // Adicionando pt-BR como padrão
        @Query("region") region: String = "BR" // Adicionando BR como padrão para relevância
    ): MovieListResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
        @Query("language") language: String = "pt-BR",
        @Query("region") region: String = "BR"
    ): MovieListResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
        @Query("language") language: String = "pt-BR",
        @Query("region") region: String = "BR"
    ): MovieListResponse


    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "pt-BR",
        @Query("append_to_response") appendToResponse: String? = "videos,credits,reviews" // Para pegar mais dados
    ): MovieDetailsDto // Precisaremos criar MovieDetailsDto

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("language") language: String = "pt-BR",
        @Query("include_adult") includeAdult: Boolean = false
    ): MovieListResponse

    // Poderíamos adicionar outros endpoints como:
    // - Filmes em cartaz (now_playing)
    // - Filmes por gênero
    // - Recomendações
    // - etc.
}
