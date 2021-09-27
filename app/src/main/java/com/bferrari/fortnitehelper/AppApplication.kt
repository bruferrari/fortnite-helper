package com.bferrari.fortnitehelper

import android.app.Application
import com.bferrari.features.shop.di.DIShopModules
import org.koin.core.context.startKoin

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(DIShopModules.modules())
        }
    }
}