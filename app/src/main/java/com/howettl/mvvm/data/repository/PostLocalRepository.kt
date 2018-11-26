package com.howettl.mvvm.data.repository

import com.howettl.mvvm.data.database.PostDao
import com.howettl.mvvm.data.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PostLocalRepository(private val postDao: PostDao) {

    suspend fun getAll(): List<Post> {
        return withContext(Dispatchers.IO) {
            async { postDao.all }.await()
        }
    }

    suspend fun getByUserId(userId: Int): List<Post> {
        return withContext(Dispatchers.IO) {
            async { postDao.getByUserId(userId) }.await()
        }
    }

    suspend fun insertAll(vararg posts: Post) {
        withContext(Dispatchers.IO) {
            launch { postDao.insertAll(*posts) }
        }
    }

    suspend fun count(): Int {
        return withContext(Dispatchers.IO) {
            async { postDao.count }.await()
        }
    }

}