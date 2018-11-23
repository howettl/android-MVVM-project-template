package com.howettl.mvvm.injection.module

import android.content.Context
import androidx.room.Room
import com.howettl.mvvm.data.PostDao
import com.howettl.mvvm.data.database.AppDatabase
import com.howettl.mvvm.data.repository.PostLocalRepository
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object DatabaseModule {

    @Provides
    @Reusable
    @JvmStatic
    internal fun providesPostLocalRepository(postDao: PostDao): PostLocalRepository {
        return PostLocalRepository(postDao)
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