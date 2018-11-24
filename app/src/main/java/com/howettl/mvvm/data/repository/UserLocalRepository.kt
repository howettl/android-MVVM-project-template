package com.howettl.mvvm.data.repository

import com.howettl.mvvm.data.database.UserDao
import com.howettl.mvvm.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserLocalRepository(private val userDao: UserDao) {

    suspend fun getAll(): List<User> {
        return withContext(Dispatchers.IO) {
            async { userDao.all }.await()
        }
    }

    suspend fun getById(id: Int): User? {
        return withContext(Dispatchers.IO) {
            async { userDao.getById(id) }.await()
        }
    }

    suspend fun insert(vararg users: User) {
        withContext(Dispatchers.IO) {
            launch { userDao.insert(*users) }
        }
    }

    suspend fun count(): Int {
        return withContext(Dispatchers.IO) {
            async { userDao.count }.await()
        }
    }

}