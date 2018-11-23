package com.howettl.mvvm.injection.module

import android.content.Context
import androidx.room.Room
import com.howettl.mvvm.model.PostDao
import com.howettl.mvvm.model.database.AppDatabase
import com.howettl.mvvm.model.repository.PostRepository
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object DatabaseModule {

    @Provides
    @Reusable
    @JvmStatic
    internal fun providesPostRepository(postDao: PostDao): PostRepository {
        return PostRepository(postDao)
    }

    @Provides
    @Reusable
    @JvmStatic
    internal fun providesPostDao(db: AppDatabase): PostDao {
        return db.postDao()
    }

    @Provides
    @Reusable
    @JvmStatic
    internal fun providesAppDatabase(context: Context): AppDatabase =
            Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "posts").build()

}