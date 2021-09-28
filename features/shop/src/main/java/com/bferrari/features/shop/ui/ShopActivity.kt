package com.bferrari.features.shop.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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

            when(val shopState = state) {
                is ShopUiState.Success -> renderList(shopState.shopResponse.data?.featured?.entries)
                is ShopUiState.Error -> displayError()
                is ShopUiState.Loading -> displayLoading()
            }
        }
    }

    @Composable
    private fun renderList(entries: List<Entry>?) {
        if (entries == null) return
        ShopItemsList(entries[0].items)
    }

    @Composable
    fun ShopItemsList(shopItems: List<ShopItem>) {
        Box(modifier = Modifier.fillMaxSize())
        LazyColumn {
            items(shopItems) { item ->
                ShopItem(item)
            }
        }
    }

    @Composable
    fun ShopItem(item: ShopItem) {
        Text(item.name)
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