package com.howettl.mvvm.ui.user

import android.content.Context
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.howettl.mvvm.base.AsyncViewModel
import com.howettl.mvvm.data.model.Post
import com.howettl.mvvm.data.model.User
import com.howettl.mvvm.data.repository.PostRepository
import com.howettl.mvvm.data.repository.UserRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class UserDetailViewModel(context: Context) : AsyncViewModel(context) {

    @Inject
    lateinit var userRepository: UserRepository
    @Inject
    lateinit var postRepository: PostRepository

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()

    var posts: LiveData<List<Post>>? = null
    var user: LiveData<User>? = null

    fun loadUser(userId: Int) {
        posts = postRepository.getCachedPostsByUserId(userId)
        user = userRepository.getCachedUserById(userId)

        launch {
            loadingVisibility.value = View.VISIBLE
            errorMessage.value = null

            try {
                val updatedUser = userRepository.getRemoteUserById(userId)
                userRepository.insertCached(updatedUser)
            } catch (e: Exception) {
                Timber.e(e)
            }

            try {
                val updatedPosts = postRepository.getRemotePostsByUserId(userId)
                postRepository.insertCached(*updatedPosts.toTypedArray())
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }
}