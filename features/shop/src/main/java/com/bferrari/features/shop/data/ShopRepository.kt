package com.bferrari.features.shop.data

import com.bferrari.features.shop.ShopStore
import com.bferrari.fortnitehelper.core.data.entities.ShopEntry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.*
import timber.log.Timber

interface ShopDataSource {
    fun fetchLocalShopItems(): List<ShopEntry>
    suspend fun fetchShopItems(): Flow<List<ShopEntry>>
    suspend fun shouldFetchRemote(): Boolean
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

    override fun fetchLocalShopItems(): List<ShopEntry> = shopStore.getShopEntries()

    override suspend fun fetchShopItems(): Flow<List<ShopEntry>> = flow {
        // TODO: decide when fetch from local store or remote api
        if (shouldFetchRemote()) {
            shopFetcher.invoke()
                .collect { shopEntries ->
                    shopStore.storeShopEntries(shopEntries)
                    emit(shopEntries)
                }
        } else {
            fetchLocalShopItems()
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun shouldFetchRemote(): Boolean {
        val shopListLastUpdatedAt = shopStore.getLastUpdatedAt().firstOrNull()
        val defaultTimeZone = TimeZone.currentSystemDefault()
        val date = shopListLastUpdatedAt?.toInstant()
        val now = Clock.System.now()
        val next = date?.plus(1, DateTimeUnit.DAY, defaultTimeZone)

        Timber.d("collected: $shopListLastUpdatedAt")
        Timber.d("date: ${date.toString()}")
        Timber.d("today: $now")
        Timber.d("tomorrow: $next")

        if (next == null) {
            Timber.e("Cannot handle next day calculations")
            return true
        }

        val remainingHoursToUpdate = now.until(next, DateTimeUnit.HOUR)
        Timber.d("updateWhen: $remainingHoursToUpdate")
        return remainingHoursToUpdate < 0
    }
}
