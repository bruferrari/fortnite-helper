package com.bferrari.fortnitehelper.core.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bferrari.fortnitehelper.core.data.dao.ShopEntryDao
import com.bferrari.fortnitehelper.core.data.entities.ShopEntry

@Database(entities = [ShopEntry::class], version = AppDatabase.DB_VERSION)
abstract class AppDatabase : RoomDatabase() {

    abstract fun shopEntryDao(): ShopEntryDao

    companion object {
        const val DB_VERSION = 1
    }
}