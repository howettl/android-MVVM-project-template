package com.howettl.mvvm.ui.post

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.howettl.mvvm.R
import com.howettl.mvvm.base.BaseViewModel
import com.howettl.mvvm.io.PostApi
import com.howettl.mvvm.model.Post
import com.howettl.mvvm.model.repository.PostRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class PostListViewModel(context: Context): BaseViewModel(context) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    @Inject
    lateinit var postApi: PostApi
    @Inject
    lateinit var postRepository: PostRepository

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadPosts() }

    val postListAdapter = PostListAdapter()

    init {
        loadPosts()
    }

    private fun loadPosts() {
        uiScope.launch {
            onStartedLoadingPosts()

            val cachedPosts = postRepository.getAll()
            if (cachedPosts.isEmpty()) {
                try {
                    val posts = postApi.getPosts().await()
                    onLoadedPosts(posts)
                    postRepository.insertAll(*posts.toTypedArray())
                } catch (e: Exception) {
                    onErrorLoadingPosts(e)
                }
            } else onLoadedPosts(cachedPosts)

            onFinishedLoadingPosts()
        }
    }

    private fun onLoadedPosts(posts: List<Post>) {
        postListAdapter.updatePostList(posts)
    }

    private fun onErrorLoadingPosts(error: Throwable) {
        Timber.e(error)
        errorMessage.value = R.string.an_error_occurred_while_loading_posts
    }

    private fun onStartedLoadingPosts() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onFinishedLoadingPosts() {
        loadingVisibility.value = View.GONE
    }

    override fun onCleared() {
        super.onCleared()

        viewModelJob.cancel()
    }
}