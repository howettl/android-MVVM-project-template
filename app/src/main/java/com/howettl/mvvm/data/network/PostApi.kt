package com.howettl.mvvm.data.network

import com.howettl.mvvm.data.model.Post
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface PostApi {
    @GET("/posts")
    fun getPosts(): Deferred<List<Post>>
}