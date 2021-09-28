package com.bferrari.fortnitehelper.core.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

class HttpClient {

    private fun httpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    inline fun <reified T> apiService(): T =
        Retrofit.Builder()
            .baseUrl("")
            .client(httpClient())
            .addConverterFactory()
}