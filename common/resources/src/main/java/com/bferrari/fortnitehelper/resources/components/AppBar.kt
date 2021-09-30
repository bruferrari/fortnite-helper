package com.bferrari.fortnitehelper.resources.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.bferrari.common.resources.R

@Composable
fun AppBar(title: String = stringResource(id = R.string.app_name)) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.h1
            )
        },
        backgroundColor = MaterialTheme.colors.secondary,
        contentColor = MaterialTheme.colors.onBackground,
    )
}