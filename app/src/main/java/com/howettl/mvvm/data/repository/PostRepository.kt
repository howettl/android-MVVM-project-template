package com.howettl.mvvm.data.repository

import androidx.lifecycle.LiveData
import com.howettl.mvvm.data.database.PostDao
import com.howettl.mvvm.data.model.Post
import com.howettl.mvvm.data.network.PostApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PostRepository(private val postDao: PostDao, private val postApi: PostApi) {

    fun getCachedPosts(): LiveData<List<Post>> = postDao.all()

    suspend fun getRemotePosts(): List<Post> {
        return withContext(Dispatchers.IO) {
            postApi.getPosts().await()
        }
    }

    fun getCachedPostsByUserId(userId: Int): LiveData<List<Post>> = postDao.getByUserId(userId)

    suspend fun getRemotePostsByUserId(userId: Int): List<Post> {
        return withContext(Dispatchers.IO) {
            postApi.getPostsByUser(userId).await()
        }
    }

    suspend fun insertCached(vararg posts: Post) {
        withContext(Dispatchers.IO) {
            postDao.insertAll(*posts)
        }
    }

    suspend fun countCached(): Int {
        return withContext(Dispatchers.IO) {
            postDao.count
        }
    }

}