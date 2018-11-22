package com.howettl.mvvm.base

import android.app.Application
import com.howettl.mvvm.BuildConfig
import timber.log.Timber

class BaseApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}