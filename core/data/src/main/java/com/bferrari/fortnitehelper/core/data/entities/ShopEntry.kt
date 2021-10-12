package com.bferrari.fortnitehelper.core.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shop_entries")
data class ShopEntry(
    @PrimaryKey val uid: Int? = null,
    @ColumnInfo(name = "title") val title: String? = null,
    @ColumnInfo(name = "description") val description: String? = null,
    @ColumnInfo(name = "image_url") val imageUrl: String? = null,
    @ColumnInfo(name = "bundle_url") val bundleUrl: String? = null,
    @ColumnInfo(name = "icon_url") val iconUrl: String? = null,
    @ColumnInfo(name = "rarity") val rarity: EntryRarity? = null, //type converter json or foreign key
    @ColumnInfo(name = "items") val items: List<ShopItem>, //type converter
    @ColumnInfo(name = "regular_price") val regularPrice: Long,
    @ColumnInfo(name = "final_price") val finalPrice: Long,
    @ColumnInfo(name = "bundle") val bundle: ShopBundle? = null //type converter
) {
    val hasSubtitle: Boolean
        get() = title != description
}

data class ShopBundle(
    val name: String?,
    val info: String?,
    val image: String?
)
