package com.howettl.mvvm.injection

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.howettl.mvvm.ui.post.PostListViewModel

class ViewModelFactory(private val context: Context): ViewModelProvider.Factory {

    override fun <T: ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(PostListViewModel::class.java)) {
            return PostListViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}