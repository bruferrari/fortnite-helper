package com.bferrari.features.shop.models

data class ShopItem(
    val name: String,
    val description: String,
    val rarity: String,
    val imageUrl: String? = null
)
