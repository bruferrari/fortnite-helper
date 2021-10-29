package com.bferrari.features.shop.data

import com.bferrari.features.shop.ShopStore
import com.bferrari.fortnitehelper.core.data.entities.ShopEntry
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

interface ShopDataSource {
    suspend fun fetchShopItems(): Flow<List<ShopEntry>>
}

class ShopRepository(
    private val shopFetcher: ShopFetcher,
    private val shopStore: ShopStore,
    externalScope: CoroutineScope
) : ShopDataSource {

    private lateinit var entriesLastUpdatedAt: String

    init {
        externalScope.launch {
            shopFetcher.entriesLastUpdatedAt.collect {
                //TODO: remove in order to use data store
                entriesLastUpdatedAt = it ?: ""
            }
        }
    }
    
    override suspend fun fetchShopItems(): Flow<List<ShopEntry>> = flow {
       shopFetcher.invoke()
           .collect { shopEntries ->
               shopStore.storeShopEntries(shopEntries)
               emit(shopEntries)
           }
    }.flowOn(Dispatchers.IO)
}
