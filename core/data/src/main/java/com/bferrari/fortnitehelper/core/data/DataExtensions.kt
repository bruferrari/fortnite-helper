package com.bferrari.fortnitehelper.core.data

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

object LocalStore {
    const val COMPANION_PREFERENCES = "companion_prefs"
}

val Context.dataStore by preferencesDataStore(name = LocalStore.COMPANION_PREFERENCES)