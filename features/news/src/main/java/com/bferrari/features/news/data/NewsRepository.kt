package com.bferrari.features.news.data

import com.bferrari.features.news.NewsService
import com.bferrari.features.news.data.remote.NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

interface NewsDataSource {
    suspend fun fetchNews(): NewsResponse
}

class FortniteNewsDataSource(
    private val apiService: NewsService
) : NewsDataSource {
    override suspend fun fetchNews(): NewsResponse = withContext(Dispatchers.IO) {
        apiService.getNews()
    }
}

class NewsRepository(
    private val dataSource: NewsDataSource
) {
    suspend fun fetchNews(): Flow<NewsResponse> = flow {
        emit(dataSource.fetchNews())
    }.flowOn(Dispatchers.IO)
}