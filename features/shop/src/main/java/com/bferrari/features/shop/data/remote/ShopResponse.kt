package com.bferrari.features.shop.data.remote

import kotlinx.serialization.Serializable

@Serializable data class ShopResponse(
    val data: Data? = null
)

@Serializable data class Data(
    val hash: String,
    val date: String,
    val featured: ListType? = null,
    val daily: ListType? = null,
    val specialFeatured: ListType? = null
)

@Serializable data class ListType(
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
    val items: List<ShopItemResponse>,
    val regularPrice: Long,
    val finalPrice: Long,
    val bundle: Bundle? = null
)

@Serializable data class Bundle(
    val name: String,
    val info: String,
    val image: String
)
