package com.bferrari.features.shop.data

import com.bferrari.features.shop.ShopStore
import com.bferrari.fortnitehelper.core.data.entities.ShopEntry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

interface ShopDataSource {
    suspend fun fetchLocalShopItems(): Flow<List<ShopEntry>>
    suspend fun fetchShopItems(): Flow<List<ShopEntry>>
}

class ShopRepository(
    private val shopFetcher: ShopFetcher,
    private val shopStore: ShopStore,
    externalScope: CoroutineScope
) : ShopDataSource {

    init {
        externalScope.launch {
            shopFetcher.entriesLastUpdatedAt.collect { lastUpdatedAt ->
                shopStore.updateLastUpdatedAt(lastUpdatedAt)
            }
        }
    }

    override suspend fun fetchShopItems(): Flow<List<ShopEntry>> = flow {
        // TODO: decide when fetch from local store or remote api
        val lastUpdatedAt = shopStore.getLastUpdatedAt().firstOrNull()
        Timber.d("collected: $lastUpdatedAt")

        shopFetcher.invoke()
            .collect { shopEntries ->
                shopStore.storeShopEntries(shopEntries)
                emit(shopEntries)
            }
    }.flowOn(Dispatchers.IO)
    
    override suspend fun fetchLocalShopItems(): Flow<List<ShopEntry>> {
        // TODO: needs to be implemented
        return flow {  }
    }
}
