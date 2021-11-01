package com.bferrari.features.shop.data

import com.bferrari.features.shop.ShopStore
import com.bferrari.fortnitehelper.core.data.entities.ShopEntry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.*
import timber.log.Timber
import java.lang.Exception

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
        val shopListLastUpdatedAt =
            shopStore.getLastUpdatedAt().firstOrNull()?.toInstant() ?: return true

        val defaultTimeZone = TimeZone.currentSystemDefault()
        val now = Clock.System.now()

        val next: Instant = try {
            shopListLastUpdatedAt.plus(1, DateTimeUnit.DAY, defaultTimeZone)
        } catch (e: Exception) {
            when (e) {
                is DateTimeArithmeticException -> Timber.e(e, "Cannot handle next day calculations, fetching remote...")
                else -> Timber.e(e, "Unexpected error occurred when trying to calculate next fetch time, fetching remote...")
            }
            return true
        }

        Timber.d( message =
                "{" +
                "\nListings last updated at: $shopListLastUpdatedAt" +
                "\nCurrent device time is: $now" +
                "\nNext listings update at: $next" +
                "\n}"
        )

        val remainingHoursToUpdate = now.until(next, DateTimeUnit.HOUR)
        Timber.d("Must update listings in: $remainingHoursToUpdate hours")

        return remainingHoursToUpdate < 0
    }
}
