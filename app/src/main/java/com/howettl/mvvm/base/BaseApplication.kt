package com.howettl.mvvm.base

import android.app.Application
import com.howettl.mvvm.BuildConfig
import com.howettl.mvvm.injection.component.ApplicationComponent
import com.howettl.mvvm.injection.component.DaggerApplicationComponent
import timber.log.Timber

class BaseApplication: Application() {

    val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
            .context(this)
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}