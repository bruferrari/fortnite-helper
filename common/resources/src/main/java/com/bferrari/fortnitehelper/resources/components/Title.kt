package com.bferrari.fortnitehelper.resources.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Title(
    modifier: Modifier = Modifier,
    text: String
) = Text(
    modifier = modifier,
    text = text.toUpperCase(),
    style = MaterialTheme.typography.h1,
    maxLines = 1
)