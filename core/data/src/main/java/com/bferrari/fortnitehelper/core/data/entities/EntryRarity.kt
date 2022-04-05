package com.bferrari.fortnitehelper.core.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class EntryRarity(
    val value: String? = null,
    val color: Long?
)

