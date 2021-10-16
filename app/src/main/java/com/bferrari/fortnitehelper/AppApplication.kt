package com.bferrari.fortnitehelper

import android.app.Application
import com.bferrari.fortnitehelper.di.DIManager
import timber.log.Timber

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        DIManager.start(applicationContext)
        timberPlant()
    }

    private fun timberPlant() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}