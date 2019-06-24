package com.howettl.mvvm.ui.user

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.howettl.mvvm.base.AsyncViewModel
import com.howettl.mvvm.data.model.Post
import com.howettl.mvvm.data.model.User
import com.howettl.mvvm.data.repository.PostRepository
import com.howettl.mvvm.data.repository.UserRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserDetailViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository
) : AsyncViewModel() {

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()

    lateinit var posts: LiveData<List<Post>>
    lateinit var user: LiveData<User>

    fun loadUser(userId: Int) {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null

        posts = postRepository.getPostsByUser(userId)
        user = userRepository.getUserById(userId)

        launch { userRepository.refreshUser(userId) }
        launch { postRepository.refreshPosts(userId) }
    }
}