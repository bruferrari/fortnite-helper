package com.bferrari.fortnitehelper.core.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class ShopBundle(
    val name: String?,
    val info: String?,
    val image: String?
)