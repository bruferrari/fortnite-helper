package com.bferrari.fortnitehelper.core.data.room

import androidx.room.TypeConverter
import com.bferrari.fortnitehelper.core.data.entities.EntryRarity
import com.bferrari.fortnitehelper.core.data.entities.ShopBundle
import com.bferrari.fortnitehelper.core.data.entities.ShopItem
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSerializationApi::class)
class Converters {

    @TypeConverter
    fun fromEntryRarityToJson(entryRarity: EntryRarity) = entryRarity.toJson()

    @TypeConverter
    fun fromJsonToEntryRarity(json: String) = json.to<EntryRarity>()

    @TypeConverter
    fun fromShopItemsToJson(shopItems: List<ShopItem>) = shopItems.toJson()

    @TypeConverter
    fun fromJsonToShopItems(json: String) = json.to<List<ShopItem>>()

    @TypeConverter
    fun fromShopBundleToJson(shopBundle: ShopBundle) = shopBundle.toJson()

    @TypeConverter
    fun fromJsonToShopBundle(json: String) = json.to<ShopBundle>()

    private inline fun <reified T> T.toJson(): String =
        Json.encodeToString(this)

    private inline fun <reified T> String.to(): T =
        Json.decodeFromString(this)
}