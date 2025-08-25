package br.com.ramonmluz.moviehub.network.okhttp.interceptor

import android.content.Context
import br.com.ramonmluz.moviehub.R
import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        //TODD Save token with security
        val authToken = context.getString(R.string.auth_token)
        val request = chain.request().newBuilder()
            .addHeader(ACCEPT,  "application/json")
            .addHeader(AUTHORIZATION, authToken)
            .build()
        val gitHubApiVersionData = "2022-11-28"
        return chain.proceed(request)
    }

    companion object {
        private const val AUTHORIZATION = "Authorization"
        private const val ACCEPT = "accept"
    }

}