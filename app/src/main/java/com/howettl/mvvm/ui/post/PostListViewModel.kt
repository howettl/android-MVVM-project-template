package com.howettl.mvvm.ui.post

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.howettl.mvvm.R
import com.howettl.mvvm.base.BaseViewModel
import com.howettl.mvvm.io.PostApi
import com.howettl.mvvm.model.Post
import com.howettl.mvvm.model.PostDao
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Inject

class PostListViewModel(private val postDao: PostDao): BaseViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    @Inject
    lateinit var postApi: PostApi

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

            val cachedPosts = async(Dispatchers.IO) { postDao.all }.await()
            if (cachedPosts.isEmpty()) {
                try {
                    val posts = postApi.getPosts().await()
                    onLoadedPosts(posts)
                    launch(Dispatchers.IO) { postDao.insertAll(*posts.toTypedArray()) }
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