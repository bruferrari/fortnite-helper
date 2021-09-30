package com.bferrari.features.shop.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class ShopItemResponse(
    val name: String,
    val description: String,
    val rarity: Rarity,
    val images: Images? = null
)

@Serializable
data class Rarity(
    val value: String,
    val displayValue: String
)

@Serializable
data class Images(
    val smallIcon: String? = null,
    val icon: String? = null,
    val featured: String? = null
)
