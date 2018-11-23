package com.howettl.mvvm.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Post(val userId: Int, @field:PrimaryKey val id: Int, val title: String, val body: String)