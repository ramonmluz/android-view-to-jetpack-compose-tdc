package br.com.ramonmluz.moviehub.di

import br.com.ramonmluz.moviehub.network.okhttp.interceptor.AuthenticationInterceptor
import br.com.ramonmluz.moviehub.network.okhttp.interceptor.OkhttpFactory
import br.com.ramonmluz.moviehub.network.provider.HttpInterceptorProvider.provideHttpInterceptor
import br.com.ramonmluz.moviehub.network.provider.MovieServiceApiProvider.providerMovieServiceApi
import br.com.ramonmluz.moviehub.network.provider.RetrofitProvider.provideRetrofit
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

class NetworkModule {
    fun load() = loadKoinModules(module {
        factory { provideHttpInterceptor() }
        factory { AuthenticationInterceptor(get()) }
        factory { OkhttpFactory(get(), get()) }
        single { provideRetrofit(get()) }
        factory { providerMovieServiceApi(get()) }
    })
}