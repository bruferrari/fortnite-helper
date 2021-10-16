package com.bferrari.fortnitehelper.core.data.entities

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import kotlinx.serialization.Contextual
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
data class EntryRarity(
    val value: String?,
    @Serializable(with = ColorAsARGBSerializer::class) val color: Color?
)

object ColorAsARGBSerializer : KSerializer<Color> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("Color", PrimitiveKind.INT)

    override fun deserialize(decoder: Decoder): Color =
        Color(decoder.decodeInt())

    override fun serialize(encoder: Encoder, value: Color) =
        encoder.encodeInt(value.toArgb())

}