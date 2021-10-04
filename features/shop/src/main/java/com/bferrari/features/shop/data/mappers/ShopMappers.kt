package com.bferrari.features.shop.data.mappers

import androidx.compose.ui.graphics.Color
import com.bferrari.features.shop.data.remote.Entry
import com.bferrari.features.shop.data.remote.Rarity
import com.bferrari.features.shop.data.remote.RarityTypes
import com.bferrari.features.shop.data.remote.ShopItemResponse
import com.bferrari.features.shop.models.EntryRarity
import com.bferrari.features.shop.models.ShopEntry
import com.bferrari.features.shop.models.ShopItem

fun ShopItemResponse.toShopItem() = ShopItem(
    name = name,
    description = description,
    rarity = rarity?.displayValue,
    imageUrl = images?.featured ?: images?.icon
)

fun List<ShopItemResponse>.toShopItemList() = map { it.toShopItem() }

fun Entry.toShopEntry() = ShopEntry(
    title = bundle?.name ?: items.firstOrNull()?.name,
    description = bundle?.info ?: items.firstOrNull()?.name,
    imageUrl = bundle?.image ?: items.firstOrNull()?.images?.featured,
    iconUrl = items.firstOrNull()?.images?.icon,
    rarity = items.firstOrNull()?.rarity?.toEntryRarity(),
    items = items.toShopItemList(),
    regularPrice = regularPrice,
    finalPrice = finalPrice,
    bundle = bundle
)

fun List<Entry>.toShopEntryList(): List<ShopEntry> = map { it.toShopEntry() }

fun Rarity.toEntryRarity() = EntryRarity(
    value = value?.name,
    color = findRarityType(backendValue.substringAfter("::"))?.toColor()
)

fun RarityTypes.toColor(): Color = when (this) {
    RarityTypes.COMMON -> Color(0xFFFFFFFF)
    RarityTypes.UNCOMMON -> Color(0xFF319236)
    RarityTypes.RARE -> Color(0xFF4C51F7)
    RarityTypes.EPIC -> Color(0xFF9D4DBB)
    RarityTypes.LEGENDARY -> Color(0xFFF3AF19)
    RarityTypes.MYTHIC -> Color(0xFF000000)
}

fun findRarityType(value: String) = RarityTypes
    .values()
    .find {
        value.equals(it.name, ignoreCase = true)
    }