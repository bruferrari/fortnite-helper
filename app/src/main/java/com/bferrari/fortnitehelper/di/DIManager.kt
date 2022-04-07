package com.bferrari.fortnitehelper.di

import android.content.Context
import com.bferrari.features.news.di.NewsModules
import com.bferrari.features.shop.di.ShopModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

object DIManager {

    @JvmStatic
    fun start(
        androidContext: Context,
        modules: List<Module> = modules()
    ) {
        startKoin {
            modules(modules)
            androidContext(androidContext)
        }
    }

    private fun modules(): List<Module> = mutableListOf<Module>()
        .apply {
            addAll(DataModules.modules)
            addAll(ShopModules.modules)
            addAll(NewsModules.modules)
        }
}