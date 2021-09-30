package com.bferrari.features.shop.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bferrari.common.resources.R
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
            val state by viewModel.shopState.collectAsState()

            when(val shopState: ShopUiState = state) {
                is ShopUiState.Success -> RenderList(shopState.shopResponse.data?.featured?.items?.toShopItemList())
                is ShopUiState.Error -> DisplayError()
                is ShopUiState.Loading -> DisplayLoading()
            }
        }
    }


    @Composable
    private fun RenderList(items: List<ShopItem>?) {
        if (items == null) return

        ZeroPointDesignSystem {
            ShopItemsList(items)
        }
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

    @Preview(showSystemUi = true)
    @Composable
    fun ShopItem(
        @PreviewParameter(SampleShopItemProvider::class) item: ShopItem
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                modifier = Modifier.absolutePadding(left = 8.dp, top = 8.dp),
                text = item.name,
                style = textStyle()
            )
            Text(
                modifier = Modifier.absolutePadding(left = 8.dp, bottom = 8.dp),
                text = item.description,
                style = textStyle(size = 16.sp)
            )
            Divider()
        }
    }

    @Composable
    private fun textStyle(size: TextUnit = 24.sp) = LocalTextStyle.current.copy(
        fontFamily = FontFamily(
            Font(R.font.fortnite, FontWeight.Normal)
        ),
        color = colorResource(id = R.color.gray),
        fontSize = size
    )

    @Composable
    private fun DisplayLoading() {
        Column(
            modifier = Modifier.fillMaxSize(),
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
        Text("error")
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