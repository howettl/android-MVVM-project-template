package com.howettl.mvvm.injection.module

import com.howettl.mvvm.data.database.PostDao
import com.howettl.mvvm.data.database.UserDao
import com.howettl.mvvm.data.network.PostApi
import com.howettl.mvvm.data.network.UserApi
import com.howettl.mvvm.data.repository.PostRepository
import com.howettl.mvvm.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object RepositoryModule {
    @Provides
    @Singleton
    internal fun providesUserRepository(userDao: UserDao, userApi: UserApi): UserRepository =
        UserRepository(userDao, userApi)

    @Provides
    @Singleton
    internal fun providesPostLocalRepository(postDao: PostDao, postApi: PostApi): PostRepository =
        PostRepository(postDao, postApi)
}