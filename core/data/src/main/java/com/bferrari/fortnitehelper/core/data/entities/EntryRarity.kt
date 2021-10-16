package com.bferrari.fortnitehelper.core.data.entities

import androidx.compose.ui.graphics.Color
import com.bferrari.fortnitehelper.core.data.serializers.ColorAsARGBSerializer
import kotlinx.serialization.Serializable

@Serializable
data class EntryRarity(
    val value: String?,
    @Serializable(with = ColorAsARGBSerializer::class) val color: Color?
)

