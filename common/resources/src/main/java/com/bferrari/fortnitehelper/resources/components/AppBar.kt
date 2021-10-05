package com.bferrari.fortnitehelper.resources.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bferrari.common.resources.R
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding

@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    titleAlignment: TextAlign,
    title: String = stringResource(id = R.string.app_name),
    elevation: Dp = 4.dp,
    navigationAction: @Composable (() -> Unit)? = null,
    backgroundColor: Color = MaterialTheme.colors.secondary
) {
    Surface(
        modifier = modifier,
        elevation = elevation,
        color = backgroundColor
    ) {
        TopAppBar(
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.h1,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = titleAlignment
                    )
                }
            },
            navigationIcon = navigationAction,
            backgroundColor = Color.Transparent,
            contentColor = MaterialTheme.colors.onBackground,
            elevation = 0.dp,
            modifier = Modifier
                .statusBarsPadding()
                .navigationBarsPadding(bottom = false)
        )
    }
}