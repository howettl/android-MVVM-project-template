package com.howettl.mvvm.injection

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.howettl.mvvm.ui.user.UserListViewModel

class ViewModelFactory(private val context: Context): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T: ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserListViewModel::class.java)) {
            return UserListViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}