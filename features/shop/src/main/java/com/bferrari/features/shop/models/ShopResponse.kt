package com.bferrari.features.shop.models

import kotlinx.serialization.Serializable

@Serializable data class ShopResponse(
    val data: Data,
    val featured: Featured
)

@Serializable data class Data(
    val hash: String,
    val date: String
)

@Serializable data class Featured(
    val name: String,
    val entries: Entries
)

@Serializable data class Entries(
    val items: List<ShopItem>
)
