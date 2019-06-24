package com.howettl.mvvm.data.repository

import com.howettl.mvvm.data.database.UserDao
import com.howettl.mvvm.data.model.User
import com.howettl.mvvm.data.network.UserApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class UserRepository(private val userDao: UserDao, private val userApi: UserApi) {

    fun getUsers() = userDao.all()

    suspend fun getCachedCount() = withContext(Dispatchers.IO) {
        userDao.count
    }

    suspend fun refreshUsers() {
        withContext(Dispatchers.IO) {
            try {
                userDao.insert(*userApi.getUsers().await().toTypedArray())
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    fun getUserById(id: Int) = userDao.getById(id)

    suspend fun refreshUser(userId: Int) = withContext(Dispatchers.IO) {
        try {
            userDao.insert(userApi.getUserById(userId).await())
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

}