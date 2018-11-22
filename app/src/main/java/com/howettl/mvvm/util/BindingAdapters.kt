package com.howettl.mvvm.util

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

@BindingAdapter("mutableVisibility")
fun setMutableVisibility(view: View, visibility: MutableLiveData<Int>?) {
    view.getParentActivity()?.let { parentActivity ->
        if (visibility != null) {
            visibility.observe(parentActivity, Observer { view.visibility = it ?: View.VISIBLE })
        }
    }
}