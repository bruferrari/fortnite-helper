package com.bferrari.fortnitehelper.core.data.serializers

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object ColorAsARGBSerializer : KSerializer<Color> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("Color", PrimitiveKind.INT)

    override fun deserialize(decoder: Decoder): Color =
        Color(decoder.decodeInt())

    override fun serialize(encoder: Encoder, value: Color) =
        encoder.encodeInt(value.toArgb())

}