package com.bferrari.features.shop.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class ShopItemResponse(
    val name: String,
    val description: String,
    val rarity: Rarity? = null,
    val images: Images? = null
)

@Serializable
data class Rarity(
    val value: RarityTypes? = null,
    val displayValue: String,
    val backendValue: String
)

@Serializable
data class Images(
    val smallIcon: String? = null,
    val icon: String? = null,
    val featured: String? = null
)

@Serializable
enum class RarityTypes(
    val value: String
) {
    COMMON("Common"),
    UNCOMMON("Uncommon"),
    RARE("Rare"),
    EPIC("Epic"),
    LEGENDARY("Legendary"),
    MYTHIC("Mythic")
}