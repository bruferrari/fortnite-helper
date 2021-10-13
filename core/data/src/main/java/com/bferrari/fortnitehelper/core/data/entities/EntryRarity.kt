package com.bferrari.fortnitehelper.core.data.entities

import androidx.compose.ui.graphics.Color
import kotlinx.serialization.Serializable

@Serializable
data class EntryRarity(
    val value: String?,
    val color: Color?
)