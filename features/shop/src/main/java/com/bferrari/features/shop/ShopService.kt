package com.bferrari.features.shop

import com.bferrari.features.shop.models.ShopResponse
import retrofit2.http.GET

interface ShopService {

    @GET("v2/shop/br")
    suspend fun getCurrentShopItems(): ShopResponse
}
