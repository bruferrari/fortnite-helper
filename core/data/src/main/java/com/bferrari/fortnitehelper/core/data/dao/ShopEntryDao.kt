package com.bferrari.fortnitehelper.core.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bferrari.fortnitehelper.core.data.entities.ShopEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface ShopEntryDao {

    @Query("SELECT * FROM shop_entries")
    fun getShopEntries(): Flow<List<ShopEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertShopEntries(entries: Collection<ShopEntry>)
}