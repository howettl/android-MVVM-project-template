package com.howettl.mvvm.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.howettl.mvvm.data.model.Post

@Database(entities = [Post::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun postDao(): PostDao

}