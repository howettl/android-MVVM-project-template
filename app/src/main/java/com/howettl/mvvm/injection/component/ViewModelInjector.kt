package com.howettl.mvvm.injection.component

import android.content.Context
import com.howettl.mvvm.injection.module.DatabaseModule
import com.howettl.mvvm.injection.module.NetworkModule
import com.howettl.mvvm.injection.module.RepositoryModule
import com.howettl.mvvm.ui.user.UserDetailViewModel
import com.howettl.mvvm.ui.user.UserListViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DatabaseModule::class, RepositoryModule::class])
interface ViewModelInjector {

    fun inject(userListViewModel: UserListViewModel)
    fun inject(userDetailViewModel: UserDetailViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder

        fun databaseModule(dataModule: DatabaseModule): Builder

        fun repositoryModule(module: RepositoryModule): Builder

        @BindsInstance
        fun context(context: Context): Builder
    }

}