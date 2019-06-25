package com.howettl.mvvm.injection.component

import android.content.Context
import com.howettl.mvvm.base.InjectedFragment
import com.howettl.mvvm.injection.module.DatabaseModule
import com.howettl.mvvm.injection.module.NetworkModule
import com.howettl.mvvm.injection.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModelModule::class, DatabaseModule::class, NetworkModule::class])
interface FragmentInjector {

    fun inject(fragment: InjectedFragment)

    @Component.Builder
    interface Builder {
        fun build(): FragmentInjector

        @BindsInstance
        fun context(context: Context): Builder
    }

}