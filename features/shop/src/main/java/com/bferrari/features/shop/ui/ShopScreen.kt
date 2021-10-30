package com.bferrari.features.shop.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.bferrari.common.utils.rememberFlowWithLifecycle
import com.bferrari.common.utils.toVBucksString
import com.bferrari.features.shop.R
import com.bferrari.fortnitehelper.core.data.entities.EntryRarity
import com.bferrari.fortnitehelper.core.data.entities.ShopEntry
import com.bferrari.fortnitehelper.core.data.entities.ShopItem
import com.bferrari.features.shop.viewmodels.ShopUiState
import com.bferrari.features.shop.viewmodels.ShopViewModel
import com.bferrari.fortnitehelper.resources.components.*
import com.bferrari.fortnitehelper.resources.theme.Colors
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.flow.Flow

@Composable
fun ShopScreen(viewModel: ShopViewModel) {
    val state by rememberFlowWithLifecycle(viewModel.shopState)
        .collectAsState(
            initial = ShopUiState.Loading
        )

    when (val shopState: ShopUiState = state) {
        is ShopUiState.Success -> EntriesList(entries = shopState.entries, viewModel = viewModel)
        is ShopUiState.Error -> ErrorView()
        is ShopUiState.Loading -> LoadingView()
    }

    AppBar(
        titleAlignment = TextAlign.Center
    )
}

@Composable
fun EntriesList(
    viewModel: ShopViewModel,
    entries: List<ShopEntry>
) {
    val refreshingState by viewModel.isRefreshing.collectAsState()

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = refreshingState),
        onRefresh = { viewModel.fetchShopItems() }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(top = 56.dp)
                .statusBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(
                horizontal = 16.dp,
                vertical = 16.dp
            )
        ) {
            items(entries) { entry ->
                EntryCell(entry = entry)
            }
        }
    }
}

@Preview
@Composable
fun EntryCell(@PreviewParameter(SampleShopEntryProvider::class) entry: ShopEntry) {
    //TODO: set placeholder image when there's nothing to load
    val imageUrl = entry.bundleUrl ?: entry.imageUrl ?: entry.iconUrl ?: ""
    Card(elevation = 8.dp) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            EntryImageView(url = imageUrl)
            RarityView(entry)
            ItemIdentifierView(entry)

            PriceView(price = Price(
                regular = entry.regularPrice,
                final = entry.finalPrice
            ))
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun EntryImageView(url: String) {
    Image(
        painter = rememberImagePainter(url),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        contentScale = ContentScale.FillBounds
    )
}

@Composable
fun ItemIdentifierView(entry: ShopEntry) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Colors.BlueGray900),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Title(
            modifier = Modifier.absolutePadding(
                left = 8.dp,
                top = 8.dp,
                bottom = if (entry.hasSubtitle) 0.dp else 8.dp
            ),
            text = entry.title ?: stringResource(id = R.string.placeholder_no_title)
        )

        if (entry.hasSubtitle) {
            Subtitle(
                modifier = Modifier.absolutePadding(left = 8.dp, bottom = 8.dp),
                text = entry.description ?: stringResource(id = R.string.placeholder_no_description),
            )
        }
    }
}

@Composable
fun RarityView(entry: ShopEntry) = entry.rarity?.color?.let { color ->
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .size(8.dp)
            .background(color = color)
    )
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
        rarity = EntryRarity(value = "Legendary", Colors.PurpleLight),
        imageUrl = "",
        items = shopItemList,
        regularPrice = 1000,
        finalPrice = 800,
        bundle = null
    )

    private val second = ShopEntry(
        title = "entry 2",
        description = "entry description 2",
        rarity = EntryRarity(value = "Legendary", Colors.PurpleLight),
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