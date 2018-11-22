package com.howettl.mvvm.io

import com.howettl.mvvm.model.Post
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface PostApi {
    @GET("/posts")
    fun getPosts(): Deferred<List<Post>>
}