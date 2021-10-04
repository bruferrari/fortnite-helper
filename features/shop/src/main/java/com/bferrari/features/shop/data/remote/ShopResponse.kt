package com.bferrari.features.shop.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable data class ShopResponse(
    val data: Data? = null
)

@Serializable data class Data(
    val hash: String,
    val date: String,
    val featured: ListType? = null,
    val daily: ListType? = null,
    val specialFeatured: ListType? = null
)

@Serializable data class ListType(
    val name: String? = null,
    val entries: List<Entry>
)

@Serializable data class Entry(
    val items: List<ShopItemResponse>,
    val regularPrice: Long,
    val finalPrice: Long,
    val bundle: Bundle? = null,
    @SerialName("newDisplayAsset") val displayAssets: DisplayAssets
)

@Serializable data class Bundle(
    val name: String,
    val info: String,
    val image: String
)

@Serializable data class DisplayAssets(
    val id: String,
    @SerialName("materialInstances") val assets: List<Asset>? = null
)

@Serializable data class Asset(
    val id: String,
    val images: AssetImages,
    val colors: AssetColors
)

@Serializable data class AssetImages(
    @SerialName("OfferImage") val offerImage: String,
    @SerialName("Background") val backgroundImage: String
)

@Serializable data class AssetColors(
    @SerialName("Background_Color_A") val colorAHex: String,
    @SerialName("Background_Color_B") val colorBHex: String,
    @SerialName("FallOff_Color") val fallOffHex: String
)