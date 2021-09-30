package com.bferrari.features.shop.models

data class ShopItem(
    val name: String,
    val description: String,
    val rarity: String,
    val imageIcon: String? = null,
    val imageUrl: String? = null
)
