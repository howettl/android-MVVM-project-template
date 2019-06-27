package com.howettl.mvvm.injection.component

import android.content.Context
import com.howettl.mvvm.base.InjectedFragment
import com.howettl.mvvm.injection.module.DatabaseModule
import com.howettl.mvvm.injection.module.NetworkModule
import com.howettl.mvvm.injection.module.ViewModelModule
import com.howettl.mvvm.ui.user.UserDetailFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModelModule::class, DatabaseModule::class, NetworkModule::class])
interface ApplicationComponent {

    fun inject(fragment: InjectedFragment)

    @Component.Builder
    interface Builder {
        fun build(): ApplicationComponent

        @BindsInstance
        fun context(context: Context): Builder
    }

}