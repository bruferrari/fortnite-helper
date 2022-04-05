package com.bferrari.features.news

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import com.bferrari.fortnitehelper.resources.components.AppBar

@Composable
fun News() {
    AppBar(
        title = "News",
        titleAlignment = TextAlign.Center
    )
}

@Composable
fun NewsList() {

}