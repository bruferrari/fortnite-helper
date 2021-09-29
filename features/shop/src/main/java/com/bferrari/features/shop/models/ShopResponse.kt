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
) {
    fun getItems(): List<ShopItem> {
        val items = mutableListOf<ShopItem>()

        entries.forEach { entry ->
            items.addAll(entry.items)
        }

        return items
    }
}

@Serializable data class Entry(
    val items: List<ShopItem>
)
