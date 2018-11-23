package com.howettl.mvvm.ui.post

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.howettl.mvvm.data.Post

class PostViewModel: ViewModel() {

    val postTitle = MutableLiveData<String>()
    val postBody = MutableLiveData<String>()

    fun bind(post: Post) {
        postTitle.value = post.title
        postBody.value = post.body
    }

}