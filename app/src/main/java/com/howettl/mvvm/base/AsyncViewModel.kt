package com.howettl.mvvm.base

import android.content.Context
import androidx.lifecycle.ViewModel
import com.howettl.mvvm.injection.component.DaggerViewModelInjector
import com.howettl.mvvm.injection.component.ViewModelInjector
import com.howettl.mvvm.injection.module.DatabaseModule
import com.howettl.mvvm.injection.module.NetworkModule
import com.howettl.mvvm.injection.module.RepositoryModule
import com.howettl.mvvm.ui.user.UserDetailViewModel
import com.howettl.mvvm.ui.user.UserListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class AsyncViewModel(context: Context): ViewModel(), CoroutineScope {
    private val injector: ViewModelInjector =
            DaggerViewModelInjector
                .builder()
                .context(context)
                .networkModule(NetworkModule)
                .databaseModule(DatabaseModule)
                .repositoryModule(RepositoryModule)
                .build()

    private var viewModelJob = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + viewModelJob

    init {
        inject()
    }

    private fun inject() {
        when (this) {
            is UserListViewModel -> injector.inject(this)
            is UserDetailViewModel -> injector.inject(this)
        }
    }

    override fun onCleared() {
        super.onCleared()

        viewModelJob.cancel()
    }
}