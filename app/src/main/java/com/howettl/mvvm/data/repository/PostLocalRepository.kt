package com.howettl.mvvm.data.repository

import androidx.lifecycle.LiveData
import com.howettl.mvvm.data.database.PostDao
import com.howettl.mvvm.data.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PostLocalRepository(private val postDao: PostDao) {

    fun getAll(): LiveData<List<Post>> = postDao.all()

    fun getByUserId(userId: Int): LiveData<List<Post>> = postDao.getByUserId(userId)

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