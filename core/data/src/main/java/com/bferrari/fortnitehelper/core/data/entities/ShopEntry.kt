package com.bferrari.fortnitehelper.core.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.bferrari.fortnitehelper.core.data.room.Converters
import kotlinx.serialization.Serializable

@Entity(tableName = "shop_entries")
@TypeConverters(Converters::class)
data class ShopEntry(
    @PrimaryKey val uid: Int? = null,
    @ColumnInfo(name = "title") val title: String? = null,
    @ColumnInfo(name = "description") val description: String? = null,
    @ColumnInfo(name = "image_url") val imageUrl: String? = null,
    @ColumnInfo(name = "bundle_url") val bundleUrl: String? = null,
    @ColumnInfo(name = "icon_url") val iconUrl: String? = null,
    @ColumnInfo(name = "rarity") val rarity: EntryRarity? = null,
    @ColumnInfo(name = "items") val items: List<ShopItem>,
    @ColumnInfo(name = "regular_price") val regularPrice: Long,
    @ColumnInfo(name = "final_price") val finalPrice: Long,
    @ColumnInfo(name = "bundle") val bundle: ShopBundle? = null
) {
    val hasSubtitle: Boolean
        get() = title != description
}
