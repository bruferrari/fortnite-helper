package com.bferrari.features.shop.data.mappers

import com.bferrari.features.shop.data.remote.Entry
import com.bferrari.features.shop.data.remote.ShopItemResponse
import com.bferrari.features.shop.models.ShopEntry
import com.bferrari.features.shop.models.ShopItem

fun ShopItemResponse.toShopItem() = ShopItem(
    name = name,
    description = description,
    rarity = rarity.displayValue,
    imageUrl = images?.featured ?: images?.icon
)

fun List<ShopItemResponse>.toShopItemList() = map { it.toShopItem() }

fun Entry.toShopEntry() = ShopEntry(
    title = bundle?.name ?: items.firstOrNull()?.name,
    description = bundle?.info ?: items.firstOrNull()?.name,
    imageUrl = bundle?.image ?: items.firstOrNull()?.images?.featured,
    iconUrl = items.firstOrNull()?.images?.icon,
    rarity = items.firstOrNull()?.rarity?.displayValue,
    items = items.toShopItemList(),
    regularPrice = regularPrice,
    finalPrice = finalPrice,
    bundle = bundle
)

fun List<Entry>.toShopEntryList(): List<ShopEntry> = map { it.toShopEntry() }
