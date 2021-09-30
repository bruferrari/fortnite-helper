package com.bferrari.fortnitehelper.resources.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.bferrari.common.resources.R

private val lightColors = lightColors(
    primary = ThemeColor.PurpleLight,
    secondary = ThemeColor.PurpleDark,
    background = ThemeColor.Black,
    onBackground = ThemeColor.Gray,
    surface = ThemeColor.Teal200,
    onSurface = ThemeColor.Teal700
)

// needs to be adapted
private val darkColors = lightColors

private val fortniteFontFamily = FontFamily(
    Font(R.font.fortnite, FontWeight.Normal)
)

interface DesignSystem {
    val title: TextStyle
    val subtitle: TextStyle
}

object ZeroPoint : DesignSystem {
    override val title: TextStyle
        get() = TextStyle(
            fontFamily = fortniteFontFamily,
            color = ThemeColor.Gray,
            fontSize = 24.sp
        )

    override val subtitle: TextStyle
        get() = TextStyle(
            fontFamily = fortniteFontFamily,
            color = ThemeColor.Gray,
            fontSize = 16.sp
        )
}

private val typography = Typography(
    h1 = ZeroPoint.title,
    subtitle1 = ZeroPoint.subtitle
)

@Composable
fun ZeroPointDesignSystem(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) darkColors else lightColors,
        typography = typography
    ) {
        content()
    }
}