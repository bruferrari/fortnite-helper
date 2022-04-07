package com.bferrari.features.news.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsResponse(
    val status: Int,
    @SerialName("data")
    val news: News,
)

@Serializable
data class NewsData(
    val date: String,
    val motds: List<NewsUnit>? = null,
    val messages: List<Messages>? = null
)

@Serializable
data class NewsUnit(
    val id: String,
    val body: String,
    @SerialName("image")
    val imageUrl: String,
    @SerialName("sortingPriority")
    val priority: Int,
    val hidden: Boolean
)

@Serializable
data class Messages(
    val title: String,
    val body: String,
    @SerialName("image")
    val imageUrl: String
)

@Serializable
data class News(
    val br: NewsData? = null,
    val stw: NewsData? = null,
    val creative: NewsData? = null
)

