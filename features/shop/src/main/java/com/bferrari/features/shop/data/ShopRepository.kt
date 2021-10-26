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
    fun getEntriesLastUpdatedAt()
    fun clear()
}

class ShopRepository(
    private val shopFetcher: ShopFetcher,
    private val shopStore: ShopStore
) : ShopDataSource {

    private lateinit var entriesLastUpdatedAt: String

    private val scope = CoroutineScope(Dispatchers.IO)

    init { getEntriesLastUpdatedAt() }
    
    override suspend fun fetchShopItems(): Flow<List<ShopEntry>> = flow {
       shopFetcher.invoke()
           .collect { shopEntries ->
               shopStore.storeShopEntries(shopEntries)
               emit(shopEntries)
           }
    }.flowOn(Dispatchers.IO)

    override fun getEntriesLastUpdatedAt() {
        scope.launch {
            shopFetcher.entriesLastUpdatedAt.collect {
                entriesLastUpdatedAt = it ?: ""
            }
        }
    }

    override fun clear() {
        scope.cancel()
    }
}
