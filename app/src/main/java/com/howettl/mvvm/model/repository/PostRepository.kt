package com.howettl.mvvm.model.repository

import com.howettl.mvvm.model.Post
import com.howettl.mvvm.model.PostDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PostRepository(private val postDao: PostDao) {

    suspend fun getAll(): List<Post> {
        return withContext(Dispatchers.IO) {
            async { postDao.all }.await()
        }
    }

    suspend fun insertAll(vararg posts: Post) {
        withContext(Dispatchers.IO) {
            launch { postDao.insertAll(*posts) }
        }
    }

}