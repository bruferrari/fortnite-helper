package com.bferrari.fortnitehelper.core.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class ShopItem(
    val name: String,
    val description: String,
    val rarity: String?,
    val imageUrl: String?
)
