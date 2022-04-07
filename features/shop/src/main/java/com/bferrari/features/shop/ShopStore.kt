package com.bferrari.features.shop

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.bferrari.fortnitehelper.core.data.dao.ShopEntryDao
import com.bferrari.fortnitehelper.core.data.entities.ShopEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.io.IOException

class ShopStore(
    private val shopEntryDao: ShopEntryDao,
    private val dataStore: DataStore<Preferences>
) {

    fun storeShopEntries(entries: List<ShopEntry>) {
        shopEntryDao.insertShopEntries(entries)
    }

    fun getShopEntries(): List<ShopEntry> {
        return shopEntryDao.getShopEntries()
    }

    suspend fun updateLastUpdatedAt(lastUpdatedAt: String?) {
        if (lastUpdatedAt == null) return

        dataStore.edit { prefs ->
            prefs[shopLastUpdatedAt] = lastUpdatedAt
        }
    }

    fun getLastUpdatedAt(): Flow<String?> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Timber.e(exception, "Error trying to read preferences")
            } else {
                throw exception
            }
        }.map { prefs ->
            prefs[shopLastUpdatedAt]
        }

    private val shopLastUpdatedAt = stringPreferencesKey("last_updated_at")
}