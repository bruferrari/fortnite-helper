package com.bferrari.features.shop

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.bferrari.fortnitehelper.core.data.dao.ShopEntryDao
import com.bferrari.fortnitehelper.core.data.entities.ShopEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ShopStore(
    private val shopEntryDao: ShopEntryDao,
    private val dataStore: DataStore<Preferences>
) {

    private val SHOP_LAST_UPDATED_AT = stringPreferencesKey("last_updated_at")

    fun storeShopEntries(entries: List<ShopEntry>) {
        shopEntryDao.insertShopEntries(entries)
    }

    suspend fun updateLastUpdatedAt(lastUpdatedAt: String?) {
        if (lastUpdatedAt == null) return

        dataStore.edit { prefs ->
            prefs[SHOP_LAST_UPDATED_AT] = lastUpdatedAt
        }
    }

    fun getLastUpdatedAt(): Flow<String?> = dataStore.data.map { prefs ->
        prefs[SHOP_LAST_UPDATED_AT]
    }
}