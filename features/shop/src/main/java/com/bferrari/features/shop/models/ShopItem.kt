package com.bferrari.features.shop.models

import kotlinx.serialization.Serializable

@Serializable
data class ShopItem(
    val name: String,
    val description: String,
    val rarity: Rarity
)

@Serializable
data class Rarity(
    val value: String,
    val displayValue: String
)

@Serializable
data class Images(
    val smallIcon: String,
    val icon: String,
    val url: String
)
