package com.howettl.mvvm.ui.post

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.howettl.mvvm.R
import com.howettl.mvvm.base.BaseViewModel
import com.howettl.mvvm.data.model.Post
import com.howettl.mvvm.data.repository.PostLocalRepository
import com.howettl.mvvm.data.repository.PostRemoteRepository
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
    lateinit var postRemoteRepository: PostRemoteRepository
    @Inject
    lateinit var postLocalRepository: PostLocalRepository

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

            val cachedPosts = postLocalRepository.getAll()

            if (cachedPosts.isNotEmpty()) {
                onLoadedPosts(cachedPosts)
            }
            try {
                val updatedPosts = postRemoteRepository.getPosts()
                postLocalRepository.insertAll(*updatedPosts.toTypedArray())
                if (cachedPosts.isEmpty()) onLoadedPosts(updatedPosts)
            } catch (e: Exception) {
                Timber.e(e)
                if (cachedPosts.isEmpty()) onErrorLoadingPosts(e)
            }
        }
    }

    private fun onLoadedPosts(posts: List<Post>) {
        postListAdapter.postList = posts
        loadingVisibility.value = View.GONE
    }

    private fun onErrorLoadingPosts(error: Throwable) {
        Timber.e(error)
        errorMessage.value = R.string.an_error_occurred_while_loading_posts
    }

    private fun onStartedLoadingPosts() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    override fun onCleared() {
        super.onCleared()

        viewModelJob.cancel()
    }
}