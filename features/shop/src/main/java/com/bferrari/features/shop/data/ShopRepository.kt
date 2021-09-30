package com.bferrari.features.shop.data

import com.bferrari.features.shop.ShopService
import com.bferrari.features.shop.data.remote.ShopResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

interface ShopDataSource {
    suspend fun getShopItems(): Flow<ShopResponse>
}

class ShopRepository(
    private val shopService: ShopService
) : ShopDataSource {

    override suspend fun getShopItems(): Flow<ShopResponse> = flow {
        val response = shopService.getCurrentShopItems()
        emit(response)
    }.flowOn(Dispatchers.IO)
}
