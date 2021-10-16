package com.bferrari.features.shop

import com.bferrari.fortnitehelper.core.data.dao.ShopEntryDao
import com.bferrari.fortnitehelper.core.data.entities.ShopEntry

class ShopStore(
    private val shopEntryDao: ShopEntryDao
) {

    fun storeShopEntries(entries: List<ShopEntry>) {
        shopEntryDao.insertShopEntries(entries)
    }

}