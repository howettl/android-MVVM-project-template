package com.howettl.mvvm.data.repository

import com.howettl.mvvm.data.model.User
import com.howettl.mvvm.data.network.UserApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRemoteRepository(private val userApi: UserApi) {

    suspend fun getUsers(): List<User> {
        return withContext(Dispatchers.IO) {
            userApi.getUsers().await()
        }
    }

    suspend fun getUserById(userId: Int): User {
        return withContext(Dispatchers.IO) {
            userApi.getUserById(userId).await()
        }
    }

}