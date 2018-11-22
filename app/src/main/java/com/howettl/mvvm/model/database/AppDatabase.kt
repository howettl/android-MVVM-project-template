package com.howettl.mvvm.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.howettl.mvvm.model.Post
import com.howettl.mvvm.model.PostDao

@Database(entities = [Post::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun postDao(): PostDao

}