package com.howettl.mvvm.injection

import androidx.room.Room
import com.howettl.mvvm.BuildConfig
import com.howettl.mvvm.data.database.AppDatabase
import com.howettl.mvvm.data.network.PostApi
import com.howettl.mvvm.data.network.UserApi
import com.howettl.mvvm.data.repository.PostRepository
import com.howettl.mvvm.data.repository.UserRepository
import com.howettl.mvvm.ui.user.UserDetailViewModel
import com.howettl.mvvm.ui.user.UserListViewModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object Modules {
    private val networkModule = module {
        single {
            val retrofit: Retrofit = get()
            retrofit.create(UserApi::class.java)
        }
        single {
            val retrofit: Retrofit = get()
            retrofit.create(PostApi::class.java)
        }
        single {
            Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
        }
    }

    private val databaseModule = module {
        single {
            val db: AppDatabase = get()
            db.userDao()
        }
        single {
            val db: AppDatabase = get()
            db.postDao()
        }
        single {
            Room.databaseBuilder(get(), AppDatabase::class.java, "posts")
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    private val repositoryModule = module {
        single { UserRepository(get(), get()) }
        single { PostRepository(get(), get()) }
    }

    private val viewModelModule = module {
        viewModel { UserListViewModel(get()) }
        viewModel { UserDetailViewModel(get(), get()) }
    }

    val all: List<Module> = listOf(
        databaseModule,
        viewModelModule,
        networkModule,
        repositoryModule
    )
}