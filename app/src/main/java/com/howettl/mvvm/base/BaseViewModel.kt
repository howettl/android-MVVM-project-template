package com.howettl.mvvm.base

import androidx.lifecycle.ViewModel
import com.howettl.mvvm.injection.component.DaggerViewModelInjector
import com.howettl.mvvm.injection.component.ViewModelInjector
import com.howettl.mvvm.injection.module.NetworkModule
import com.howettl.mvvm.ui.post.PostListViewModel

abstract class BaseViewModel: ViewModel() {
    private val injector: ViewModelInjector =
            DaggerViewModelInjector
                .builder()
                .networkModule(NetworkModule)
                .build()

    init {
        inject()
    }

    private fun inject() {
        when (this) {
            is PostListViewModel -> injector.inject(this)
        }
    }
}