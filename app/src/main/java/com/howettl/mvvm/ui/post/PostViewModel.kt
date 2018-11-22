package com.howettl.mvvm.ui.post

import androidx.lifecycle.MutableLiveData
import com.howettl.mvvm.base.BaseViewModel
import com.howettl.mvvm.model.Post

class PostViewModel: BaseViewModel() {

    val postTitle = MutableLiveData<String>()
    val postBody = MutableLiveData<String>()

    fun bind(post: Post) {
        postTitle.value = post.title
        postBody.value = post.body
    }

}