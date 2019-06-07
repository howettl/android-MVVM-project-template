package com.howettl.mvvm.ui.user

import android.content.Context
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.howettl.mvvm.R
import com.howettl.mvvm.base.AsyncViewModel
import com.howettl.mvvm.data.model.Post
import com.howettl.mvvm.data.model.User
import com.howettl.mvvm.data.repository.PostLocalRepository
import com.howettl.mvvm.data.repository.PostRemoteRepository
import com.howettl.mvvm.data.repository.UserLocalRepository
import com.howettl.mvvm.data.repository.UserRemoteRepository
import com.howettl.mvvm.ui.post.PostAdapter
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class UserDetailViewModel(context: Context) : AsyncViewModel(context) {

    @Inject
    lateinit var userRemoteRepository: UserRemoteRepository
    @Inject
    lateinit var userLocalRepository: UserLocalRepository
    @Inject
    lateinit var postRemoteRepository: PostRemoteRepository
    @Inject
    lateinit var postLocalRepository: PostLocalRepository

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()

    var posts: LiveData<List<Post>>? = null
    var user: LiveData<User>? = null

    fun loadUser(userId: Int) {
        posts = postLocalRepository.getByUserId(userId)
        user = userLocalRepository.getById(userId)

        launch {
            loadingVisibility.value = View.VISIBLE
            errorMessage.value = null

            try {
                val updatedUser = userRemoteRepository.getUserById(userId)
                userLocalRepository.insert(updatedUser)
            } catch (e: Exception) {
                Timber.e(e)
            }

            try {
                val updatedPosts = postRemoteRepository.getPostsByUser(userId)
                postLocalRepository.insertAll(*updatedPosts.toTypedArray())
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }
}