package com.howettl.mvvm.data.repository

import com.howettl.mvvm.data.database.UserDao
import com.howettl.mvvm.data.model.User
import com.howettl.mvvm.data.network.UserApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserRepository(private val userDao: UserDao, private val userApi: UserApi) {

    fun getCachedUsers() = userDao.all()

    suspend fun getRemoteUsers(): List<User> {
        return withContext(Dispatchers.IO) {
            userApi.getUsers().await()
        }
    }

    fun getCachedUserById(id: Int) = userDao.getById(id)

    suspend fun getRemoteUserById(userId: Int): User {
        return withContext(Dispatchers.IO) {
            userApi.getUserById(userId).await()
        }
    }

    suspend fun insertCached(vararg users: User) {
        withContext(Dispatchers.IO) {
            userDao.insert(*users)
        }
    }

    suspend fun countCached(): Int {
        return withContext(Dispatchers.IO) {
            userDao.count
        }
    }

}