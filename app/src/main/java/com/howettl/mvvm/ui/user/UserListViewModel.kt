package com.howettl.mvvm.ui.user

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.howettl.mvvm.R
import com.howettl.mvvm.base.BaseViewModel
import com.howettl.mvvm.data.model.User
import com.howettl.mvvm.data.repository.UserLocalRepository
import com.howettl.mvvm.data.repository.UserRemoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class UserListViewModel(context: Context): BaseViewModel(context) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    @Inject
    lateinit var userRemoteRepository: UserRemoteRepository
    @Inject
    lateinit var userLocalRepository: UserLocalRepository

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadUsers() }

    val userListAdapter = UserListAdapter()

    init {
        loadUsers()
    }

    private fun loadUsers() {
        uiScope.launch {
            onStartedLoadingUsers()

            val cachedUsers = userLocalRepository.getAll()

            if (cachedUsers.isNotEmpty()) {
                onLoadedUsers(cachedUsers)
            }
            try {
                val updatedUsers = userRemoteRepository.getUsers()
                userLocalRepository.insert(*updatedUsers.toTypedArray())
                if (cachedUsers.isEmpty()) onLoadedUsers(updatedUsers)
            } catch (e: Exception) {
                Timber.e(e)
                if (cachedUsers.isEmpty()) onErrorLoadingUsers(e)
            }
        }
    }

    private fun onLoadedUsers(users: List<User>) {
        userListAdapter.userList = users
        loadingVisibility.value = View.GONE
    }

    private fun onErrorLoadingUsers(error: Throwable) {
        Timber.e(error)
        errorMessage.value = R.string.an_error_occurred_while_loading_users
    }

    private fun onStartedLoadingUsers() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    override fun onCleared() {
        super.onCleared()

        viewModelJob.cancel()
    }
}