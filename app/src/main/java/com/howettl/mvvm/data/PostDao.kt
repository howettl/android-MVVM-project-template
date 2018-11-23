package com.howettl.mvvm.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PostDao {

    @get:Query("SELECT * FROM post")
    val all: List<Post>

    @get:Query("SELECT count(*) FROM post")
    val count: Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg posts: Post)

}