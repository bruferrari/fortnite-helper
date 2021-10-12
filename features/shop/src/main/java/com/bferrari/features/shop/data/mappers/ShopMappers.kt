package com.bferrari.features.shop.data.mappers

import androidx.compose.ui.graphics.Color
import com.bferrari.features.shop.data.remote.Entry
import com.bferrari.features.shop.data.remote.Rarity
import com.bferrari.features.shop.data.remote.RarityTypes
import com.bferrari.features.shop.data.remote.ShopItemResponse
import com.bferrari.fortnitehelper.core.data.entities.EntryRarity
import com.bferrari.fortnitehelper.core.data.entities.ShopBundle
import com.bferrari.fortnitehelper.core.data.entities.ShopEntry
import com.bferrari.fortnitehelper.core.data.entities.ShopItem
import com.bferrari.fortnitehelper.resources.theme.RarityColors

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
    bundleUrl = displayAssets?.assets?.firstOrNull()?.images?.backgroundImage
        ?: bundle?.image,
    imageUrl = displayAssets?.assets?.firstOrNull()?.images?.backgroundImage
        ?: items.firstOrNull()?.images?.featured,
    iconUrl = items.firstOrNull()?.images?.icon,
    rarity = items.firstOrNull()?.rarity?.toEntryRarity(),
    items = items.toShopItemList(),
    regularPrice = regularPrice,
    finalPrice = finalPrice,
    bundle = ShopBundle(
        name = bundle?.name,
        info = bundle?.info,
        image = bundle?.image
    )
)

fun List<Entry>.toShopEntryList(): List<ShopEntry> = map { it.toShopEntry() }

fun Rarity.toEntryRarity() = EntryRarity(
    value = value?.name,
    color = findRarityType(backendValue.substringAfter("::"))?.toColor()
)

fun RarityTypes.toColor(): Color = when (this) {
    RarityTypes.COMMON -> RarityColors.Common
    RarityTypes.UNCOMMON -> RarityColors.Uncommon
    RarityTypes.RARE -> RarityColors.Rare
    RarityTypes.EPIC -> RarityColors.Epic
    RarityTypes.LEGENDARY -> RarityColors.Legendary
    RarityTypes.MYTHIC -> RarityColors.Mythic
}

private fun findRarityType(value: String) = RarityTypes
    .values()
    .find {
        value.equals(it.name, ignoreCase = true)
    }