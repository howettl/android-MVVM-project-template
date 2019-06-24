package com.howettl.mvvm.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.howettl.mvvm.data.model.Post

@Dao
interface PostDao {

    @Query("SELECT * FROM post")
    fun all(): LiveData<List<Post>>

    @get:Query("SELECT count(*) FROM post")
    val count: Int

    @Query("SELECT * FROM post WHERE userId = :userId")
    fun getByUserId(userId: Int): LiveData<List<Post>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg posts: Post)

}