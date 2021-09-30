package com.bferrari.features.shop.data.mappers

import com.bferrari.features.shop.data.remote.ShopItemResponse
import com.bferrari.features.shop.models.ShopItem

fun ShopItemResponse.toDomain() = ShopItem(
    name = name,
    description = description,
    rarity = rarity.displayValue,
    imageUrl = images?.url,
    imageIcon = images?.icon
)

fun List<ShopItemResponse>.toShopItemList() = map { it.toDomain() }