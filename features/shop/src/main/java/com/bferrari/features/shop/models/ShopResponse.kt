package com.bferrari.features.shop.models

import kotlinx.serialization.Serializable

@Serializable data class ShopResponse(
    val data: Data? = null
)

@Serializable data class Data(
    val hash: String,
    val date: String,
    val featured: ItemType? = null,
    val daily: ItemType? = null,
    val specialFeatured: ItemType? = null
)

@Serializable data class ItemType(
    val name: String? = null,
    val entries: List<Entry>
)

@Serializable data class Entry(
    val items: List<ShopItem>
)
