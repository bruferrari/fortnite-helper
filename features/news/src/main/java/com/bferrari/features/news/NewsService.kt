package com.bferrari.features.news

import com.bferrari.features.news.data.remote.NewsResponse
import retrofit2.http.GET

interface NewsService {

    @GET("v2/news")
    suspend fun getNews(): NewsResponse
}