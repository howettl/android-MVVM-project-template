package com.howettl.mvvm.data.repository

import com.howettl.mvvm.data.Post
import com.howettl.mvvm.data.network.PostApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PostRemoteRepository(private val postApi: PostApi) {

    suspend fun getPosts(): List<Post> {
        return withContext(Dispatchers.IO) {
            postApi.getPosts().await()
        }
    }

}