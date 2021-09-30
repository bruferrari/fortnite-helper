package com.bferrari.fortnitehelper

import android.app.Application
import timber.log.Timber

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        DIManager.start()
        timberPlant()
    }

    private fun timberPlant() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}