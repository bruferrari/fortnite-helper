package com.bferrari.fortnitehelper.di

import com.bferrari.features.shop.ShopService
import com.bferrari.fortnitehelper.core.network.HttpClient
import org.koin.dsl.module

object DataModules {

    val apiModules
        get() = module {
            single { HttpClient.apiService<ShopService>() }
        }
}