package com.bferrari.features.shop.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.bferrari.features.shop.R
import com.bferrari.features.shop.data.mappers.toShopEntryList
import com.bferrari.features.shop.data.remote.Data
import com.bferrari.features.shop.models.ShopEntry
import com.bferrari.features.shop.models.ShopItem
import com.bferrari.features.shop.utils.toVBucksString
import com.bferrari.features.shop.viewmodels.ShopUiState
import com.bferrari.features.shop.viewmodels.ShopViewModel
import com.bferrari.fortnitehelper.resources.components.AppBar
import com.bferrari.fortnitehelper.resources.components.ErrorView
import com.bferrari.fortnitehelper.resources.components.LoadingView
import com.bferrari.fortnitehelper.resources.theme.Colors
import com.bferrari.fortnitehelper.resources.theme.ZeroPointDesignSystem
import org.koin.android.ext.android.inject

@ExperimentalCoilApi
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
            is ShopUiState.Success -> RenderList(shopState.shopResponse.data)
            is ShopUiState.Error -> ErrorView()
            is ShopUiState.Loading -> LoadingView()
        }

        AppBar()
    }
    
    @Composable
    fun RenderList(data: Data?) {
        if (data == null) return // render a blank slate?

        val featuredItems = data.featured?.entries?.toShopEntryList()
        val dailyItems = data.daily?.entries?.toShopEntryList()
        val specialItems = data.specialFeatured?.entries?.toShopEntryList()
        
        //TODO: render 3 types of different lists (featured, daily, special)
        EntriesList(entries = featuredItems ?: emptyList())
    }
    
    @Composable
    fun EntriesList(entries: List<ShopEntry>) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.primary)
        )
        LazyColumn(
            modifier = Modifier.padding(top = 56.dp)
        ) {
            items(entries) { entry ->
                EntryCell(entry = entry)
            }
        }
    }

    @Preview
    @Composable
    fun EntryCell(@PreviewParameter(SampleShopEntryProvider::class) entry: ShopEntry) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            ItemIdentifierView(entry)

            PriceView(price = Price(
                regular = entry.regularPrice,
                final = entry.finalPrice
            ))
        }
    }

    @Composable
    fun ItemIdentifierView(entry: ShopEntry) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = rememberImagePainter(entry.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentScale = ContentScale.FillBounds
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Colors.BlueGray900),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    modifier = Modifier.absolutePadding(left = 8.dp, top = 8.dp),
                    text = entry.title ?: stringResource(id = R.string.placeholder_no_title),
                    style = MaterialTheme.typography.h1,
                    maxLines = 1
                )
                Text(
                    modifier = Modifier.absolutePadding(left = 8.dp, bottom = 8.dp),
                    text = entry.description ?: stringResource(id = R.string.placeholder_no_description),
                    style = MaterialTheme.typography.subtitle1,
                    maxLines = 1
                )
            }
        }
    }

    @Composable
    fun PriceView(price: Price) {
        val hasDiscount: Boolean = price.final != price.regular

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .size(24.dp)
                .background(color = Colors.BlueGray900Light),
            horizontalArrangement = Arrangement.End
        ) {
            if (hasDiscount) {
                Text(
                    modifier = Modifier.padding(start = 8.dp, end = 4.dp),
                    text = stringResource(id = R.string.price_regular_with_discount, price.regular),
                    style = MaterialTheme.typography.subtitle1,
                    textAlign = TextAlign.Center,
                    maxLines = 1
                )
            }

            Text(
                modifier = Modifier.padding(start = 4.dp, end = 8.dp),
                text = if (hasDiscount) stringResource(
                    id = R.string.price_final_with_discount,
                    price.final
                ) else price.final.toVBucksString(),
                style = MaterialTheme.typography.subtitle1,
                maxLines = 1
            )

            Image(
                modifier = Modifier
                    .size(30.dp)
                    .padding(end = 8.dp),
                painter = painterResource(id = R.drawable.vbuck),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
    }

    data class Price(
        val regular: Long,
        val final: Long
    )

    class SampleShopEntryProvider: PreviewParameterProvider<ShopEntry> {
        private val shopItemList = listOf(
            ShopItem(
                name = "item 1",
                description = "item description 1",
                rarity = "Epic",
                imageUrl = ""
            ),
            ShopItem(
                name = "item 2",
                description = "item description 2",
                rarity = "Epic",
                imageUrl = ""
            )
        )

        private val first = ShopEntry(
            title = "entry 1",
            description = "entry description 1",
            rarity = "Legendary",
            imageUrl = "",
            items = shopItemList,
            regularPrice = 1000,
            finalPrice = 800,
            bundle = null
        )

        private val second = ShopEntry(
            title = "entry 2",
            description = "entry description 2",
            rarity = "Legendary",
            imageUrl = "",
            items = shopItemList,
            regularPrice = 1200,
            finalPrice = 800,
            bundle = null
        )

        override val values: Sequence<ShopEntry>
            get() = sequenceOf(first, second)

        override val count: Int
            get() = values.count()
    }
}