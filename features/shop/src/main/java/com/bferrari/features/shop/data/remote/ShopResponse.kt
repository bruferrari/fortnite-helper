package com.bferrari.features.shop.data.remote

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
    val items: List<ShopItemResponse> get() = buildItems()

    private fun buildItems(): List<ShopItemResponse> {
        val items = mutableListOf<ShopItemResponse>()

        entries.forEach { entry ->
            items.addAll(entry.items)
        }

        return items.toList()
    }
}

@Serializable data class Entry(
    val items: List<ShopItemResponse>
)
