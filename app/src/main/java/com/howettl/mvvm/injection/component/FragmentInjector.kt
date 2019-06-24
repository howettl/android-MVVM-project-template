package com.howettl.mvvm.injection.component

import android.content.Context
import com.howettl.mvvm.base.InjectedFragment
import com.howettl.mvvm.injection.module.DatabaseModule
import com.howettl.mvvm.injection.module.NetworkModule
import com.howettl.mvvm.injection.module.RepositoryModule
import com.howettl.mvvm.injection.module.ViewModelModule
import com.howettl.mvvm.ui.user.UserDetailFragment
import com.howettl.mvvm.ui.user.UserListFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModelModule::class, RepositoryModule::class, DatabaseModule::class, NetworkModule::class])
interface FragmentInjector {

    fun inject(fragment: InjectedFragment)

    @Component.Builder
    interface Builder {
        fun build(): FragmentInjector

        fun repositoryModule(module: RepositoryModule): Builder

        fun databaseModule(module: DatabaseModule): Builder

        fun networkModule(module: NetworkModule): Builder

        @BindsInstance
        fun context(context: Context): Builder
    }

}