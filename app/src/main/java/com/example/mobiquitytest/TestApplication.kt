package com.example.mobiquitytest

import android.app.Application
import timber.log.Timber

class TestApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }
}