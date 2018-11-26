package com.howettl.mvvm.ui.user

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
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

class UserDetailViewModel(context: Context): AsyncViewModel(context) {

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

    var userViewModel: UserViewModel = UserViewModel()
    val postAdapter = PostAdapter()

    fun loadUser(userId: Int) {
        launch {
            loadingVisibility.value = View.VISIBLE
            errorMessage.value = null

            val cachedUser = userLocalRepository.getById(userId)
            if (cachedUser != null) {
                onLoadedUser(cachedUser)
            }

            val cachedPosts = postLocalRepository.getByUserId(userId)
            if (cachedPosts.isNotEmpty()) {
                onLoadedPosts(cachedPosts)
            }

            try {
                val updatedUser = userRemoteRepository.getUserById(userId)
                if (cachedUser == null) onLoadedUser(updatedUser)
                userLocalRepository.insert(updatedUser)
            } catch (e: Exception) {
                Timber.e(e)
                if (cachedUser == null) onErrorLoadingUser(e)
            }

            try {
                val updatedPosts = postRemoteRepository.getPostsByUser(userId)
                if (cachedPosts.isEmpty()) onLoadedPosts(updatedPosts)
                postLocalRepository.insertAll(*updatedPosts.toTypedArray())
            } catch (e: Exception) {
                Timber.e(e)
                if (cachedPosts.isEmpty()) onErrorLoadingPosts(e)
            }
        }
    }

    private fun onLoadedUser(user: User) {
        loadingVisibility.value = View.GONE
        userViewModel.bind(user)
    }

    private fun onLoadedPosts(posts: List<Post>) {
        postAdapter.posts = posts
    }

    private fun onErrorLoadingUser(error: Throwable) {
        errorMessage.value = R.string.an_error_occurred_while_loading_user_info
        userViewModel.unbind()
    }

    private fun onErrorLoadingPosts(error: Throwable) {
        errorMessage.value = R.string.an_error_occurred_while_loading_posts
    }

}