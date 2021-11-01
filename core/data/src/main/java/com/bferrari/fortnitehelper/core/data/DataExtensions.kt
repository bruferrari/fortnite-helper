package com.bferrari.fortnitehelper.core.data

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

object LocalStore {
    const val DATASTORE_COMPANION_PREFERENCES = "companion_prefs"
}

val Context.dataStore by preferencesDataStore(name = LocalStore.DATASTORE_COMPANION_PREFERENCES)