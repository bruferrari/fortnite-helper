package com.bferrari.fortnitehelper

import android.app.Application

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        DIManager.start()
    }
}