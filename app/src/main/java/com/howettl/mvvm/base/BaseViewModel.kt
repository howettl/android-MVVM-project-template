package com.howettl.mvvm.base

import android.content.Context
import androidx.lifecycle.ViewModel
import com.howettl.mvvm.injection.component.DaggerViewModelInjector
import com.howettl.mvvm.injection.component.ViewModelInjector
import com.howettl.mvvm.injection.module.DatabaseModule
import com.howettl.mvvm.injection.module.NetworkModule
import com.howettl.mvvm.ui.post.PostListViewModel
import com.howettl.mvvm.ui.user.UserListViewModel

abstract class BaseViewModel(context: Context): ViewModel() {
    private val injector: ViewModelInjector =
            DaggerViewModelInjector
                .builder()
                .context(context)
                .networkModule(NetworkModule)
                .databaseModule(DatabaseModule)
                .build()

    init {
        inject()
    }

    private fun inject() {
        when (this) {
            is PostListViewModel -> injector.inject(this)
            is UserListViewModel -> injector.inject(this)
        }
    }
}