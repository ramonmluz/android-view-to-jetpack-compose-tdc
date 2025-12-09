package br.com.ramonmluz.moviehub.network.provider

import okhttp3.logging.HttpLoggingInterceptor

object HttpInterceptorProvider {

    fun provideHttpInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }
}