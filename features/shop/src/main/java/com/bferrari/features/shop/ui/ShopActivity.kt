package com.bferrari.features.shop.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bferrari.features.shop.models.Entry
import com.bferrari.features.shop.models.ShopItem
import com.bferrari.features.shop.viewmodels.ShopUiState
import com.bferrari.features.shop.viewmodels.ShopViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import androidx.compose.foundation.lazy.items

class ShopActivity : AppCompatActivity() {

    private val viewModel: ShopViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.shopState.collect { shopState ->
                    when(shopState) {
                        is ShopUiState.Success -> renderList(shopState.shopResponse.data?.featured?.entries)
                        is ShopUiState.Error -> displayError()
                        is ShopUiState.Loading -> displayLoading()
                    }
                }
            }
        }
    }

    private fun renderList(entries: List<Entry>?) {
        if (entries == null) return

        setContent {
            ShopItemsList(entries[0].items)
        }
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

    private fun displayLoading() {
        //TODO: to be implemented
    }

    private fun displayError() {
        //TODO: to be implemented
    }
}