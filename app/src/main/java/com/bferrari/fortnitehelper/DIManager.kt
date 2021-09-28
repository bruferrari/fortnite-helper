package com.bferrari.fortnitehelper

import com.bferrari.features.shop.di.ShopModules
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

object DIManager {

    @JvmStatic
    fun start(modules: List<Module> = modules()) {
        startKoin {
            modules(modules)
        }
    }

    @JvmStatic
    fun loadModules(modules: List<Module>) {
        GlobalContext.loadKoinModules(modules)
    }

    private fun modules(): List<Module> = listOf(
        DataModules.apiModules,
        ShopModules.repositoryModules,
        ShopModules.viewModelModules
    )
}