package com.bferrari.fortnitehelper.di

import com.bferrari.features.shop.di.ShopModules
import org.koin.core.context.startKoin
import org.koin.core.module.Module

object DIManager {

    @JvmStatic
    fun start(modules: List<Module> = modules()) {
        startKoin {
            modules(modules)
        }
    }

    private fun modules(): List<Module> = listOf(
        DataModules.apiModules,
        ShopModules.repositoryModules,
        ShopModules.viewModelModules
    )
}