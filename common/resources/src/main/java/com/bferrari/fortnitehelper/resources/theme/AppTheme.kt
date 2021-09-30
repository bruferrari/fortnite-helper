package com.bferrari.fortnitehelper.resources.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

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

@Composable
fun ZeroPointDesignSystem(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) darkColors else lightColors

    MaterialTheme(colors = colors) {
        content()
    }
}