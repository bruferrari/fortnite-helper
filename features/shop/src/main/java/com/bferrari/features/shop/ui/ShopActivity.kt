package com.bferrari.features.shop.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.bferrari.features.shop.models.Entry
import com.bferrari.features.shop.models.ShopItem
import com.bferrari.features.shop.viewmodels.ShopUiState
import com.bferrari.features.shop.viewmodels.ShopViewModel
import org.koin.android.ext.android.inject

class ShopActivity : AppCompatActivity() {

    private val viewModel: ShopViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val state by viewModel.shopState.collectAsState()

            when(val shopState: ShopUiState = state) {
                is ShopUiState.Success -> renderList(shopState.shopResponse.data?.featured?.getItems())
                is ShopUiState.Error -> displayError()
                is ShopUiState.Loading -> displayLoading()
            }
        }
    }

    @Composable
    private fun renderList(items: List<ShopItem>?) {
        if (items == null) return

        ShopItemsList(items)
    }

    @Composable
    fun ShopItemsList(shopItems: List<ShopItem>) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.primary)
        )
        LazyColumn {
            items(shopItems) { item ->
                ShopItem(item)
            }
        }
    }

    @Composable
    fun ShopItem(item: ShopItem) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = item.name,

            )
            Text(text = item.description)
        }
    }

    @Composable
    private fun displayLoading() {
        CircularProgressIndicator()
    }

    @Composable
    private fun displayError() {
        Text("error")
    }
}