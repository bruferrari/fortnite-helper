package com.bferrari.features.shop.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.bferrari.features.shop.data.mappers.toShopItemList
import com.bferrari.features.shop.models.ShopItem
import com.bferrari.features.shop.viewmodels.ShopUiState
import com.bferrari.features.shop.viewmodels.ShopViewModel
import com.bferrari.fortnitehelper.resources.theme.ZeroPointDesignSystem
import org.koin.android.ext.android.inject

class ShopActivity : AppCompatActivity() {

    private val viewModel: ShopViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ZeroPointDesignSystem {
                ShopScreen()
            }
        }
    }

    @Preview
    @Composable
    fun ShopScreen() {
        val state by viewModel.shopState.collectAsState()

        when(val shopState: ShopUiState = state) {
            is ShopUiState.Success -> RenderList(shopState.shopResponse.data?.featured?.items?.toShopItemList())
            is ShopUiState.Error -> DisplayError()
            is ShopUiState.Loading -> DisplayLoading()
        }

        TopAppBar(
            title = {
                Text(
                    text = "Fortnite Companion",
                    style = MaterialTheme.typography.h1
                )
            },
            backgroundColor = MaterialTheme.colors.secondary,
            contentColor = MaterialTheme.colors.onBackground,
        )
    }

    @Composable
    private fun RenderList(items: List<ShopItem>?) {
        if (items == null) return //TODO: maybe a blank slate?
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

    @ExperimentalCoilApi
    @Preview(showSystemUi = true)
    @Composable
    fun ShopItem(
        @PreviewParameter(SampleShopItemProvider::class) item: ShopItem
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Image(
                painter = rememberImagePainter(item.imageUrl ?: item.imageIcon),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentScale = ContentScale.FillBounds
            )
            Text(
                modifier = Modifier.absolutePadding(left = 8.dp, top = 8.dp),
                text = item.name,
                style = MaterialTheme.typography.h1
            )
            Text(
                modifier = Modifier.absolutePadding(left = 8.dp, bottom = 8.dp),
                text = item.description,
                style = MaterialTheme.typography.subtitle1
            )
            Divider()
        }
    }

    @Composable
    private fun DisplayLoading() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.primary),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

            CircularProgressIndicator(
                modifier = Modifier.padding(all = 16.dp),
                color = Color.Cyan
            )
        }
    }

    @Composable
    private fun DisplayError() {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("ops, something got wrong!")
        }
    }

    class SampleShopItemProvider: PreviewParameterProvider<ShopItem> {
        private val first = ShopItem(
            name = "item 1",
            description = "item description 1",
            rarity = "Legendary",
            imageIcon = "",
            imageUrl = ""
        )

        private val second = ShopItem(
            name = "item 2",
            description = "item description 2",
            rarity = "Epic",
            imageIcon = "",
            imageUrl = ""
        )

        override val values: Sequence<ShopItem>
            get() = sequenceOf(first, second)

        override val count: Int
            get() = values.count()
    }
}