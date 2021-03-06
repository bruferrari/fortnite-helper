package com.bferrari.features.shop.data

import androidx.compose.ui.graphics.Color
import com.bferrari.common.utils.concat
import com.bferrari.features.shop.ShopService
import com.bferrari.features.shop.data.remote.*
import com.bferrari.fortnitehelper.core.data.entities.EntryRarity
import com.bferrari.fortnitehelper.core.data.entities.ShopBundle
import com.bferrari.fortnitehelper.core.data.entities.ShopEntry
import com.bferrari.fortnitehelper.core.data.entities.ShopItem
import com.bferrari.fortnitehelper.resources.theme.RarityColors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

class ShopFetcher(
    private val shopService: ShopService
) {

    private val _entriesLastUpdatedAt = MutableStateFlow<String?>(null)
    val entriesLastUpdatedAt: StateFlow<String?> get() = _entriesLastUpdatedAt

    operator fun invoke(): Flow<List<ShopEntry>> = flow {
        emit(fetchShop())
    }

    private suspend fun fetchShop(): List<ShopEntry> = withContext(Dispatchers.IO) {
        val response = shopService.getCurrentShopItems()
        _entriesLastUpdatedAt.emit(response.data?.date)
        getShopEntries(response)
    }

    private fun getShopEntries(shopResponse: ShopResponse): List<ShopEntry> {
        val featured = shopResponse.data?.featured?.entries?.toShopEntryList() ?: emptyList()
        val daily = shopResponse.data?.daily?.entries?.toShopEntryList() ?: emptyList()
        val special = shopResponse.data?.specialFeatured?.entries?.toShopEntryList() ?: emptyList()

        return featured.concat(daily, special)
    }

    private fun ShopItemResponse.toShopItem() = ShopItem(
        name = name,
        description = description,
        rarity = rarity?.displayValue,
        imageUrl = images?.featured ?: images?.icon
    )

    private fun List<ShopItemResponse>.toShopItemList() = map { it.toShopItem() }

    private fun Entry.toShopEntry() = ShopEntry(
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

    private fun List<Entry>.toShopEntryList(): List<ShopEntry> = map { it.toShopEntry() }

    private fun Rarity.toEntryRarity() = EntryRarity(
        value = value?.name,
        color = findRarityType(backendValue.substringAfter("::"))?.toColor()
    )

    private fun RarityTypes.toColor(): Long = when (this) {
        RarityTypes.COMMON -> 0xFFFFFFFF
        RarityTypes.UNCOMMON -> 0xFF319236
        RarityTypes.RARE -> 0xFF4C51F7
        RarityTypes.EPIC -> 0xFF9D4DBB
        RarityTypes.LEGENDARY -> 0xFFF3AF19
        RarityTypes.MYTHIC -> 0xFF000000
    }

    private fun findRarityType(value: String) = RarityTypes
        .values()
        .find {
            value.equals(it.name, ignoreCase = true)
        }
}