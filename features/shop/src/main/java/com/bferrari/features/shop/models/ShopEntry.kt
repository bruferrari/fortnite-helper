package com.bferrari.features.shop.models

import com.bferrari.features.shop.data.remote.Bundle

data class ShopEntry(
    val title: String? = null,
    val description: String? = null,
    val imageUrl: String? = null,
    val iconUrl: String? = null,
    val rarity: EntryRarity? = null,
    val items: List<ShopItem>,
    val regularPrice: Long,
    val finalPrice: Long,
    val bundle: Bundle? = null
) {
    val hasSubtitle: Boolean
        get() = title != description
}
