package com.howettl.mvvm.injection.component

import android.content.Context
import com.howettl.mvvm.injection.module.DatabaseModule
import com.howettl.mvvm.injection.module.NetworkModule
import com.howettl.mvvm.ui.post.PostListViewModel
import com.howettl.mvvm.ui.user.UserListViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DatabaseModule::class])
interface ViewModelInjector {

    fun inject(postListViewModel: PostListViewModel)
    fun inject(userListViewModel: UserListViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder

        fun databaseModule(databaseModule: DatabaseModule): Builder

        @BindsInstance
        fun context(context: Context): Builder
    }

}