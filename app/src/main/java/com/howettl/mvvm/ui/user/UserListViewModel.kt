package com.howettl.mvvm.ui.user

import android.content.Context
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.howettl.mvvm.base.AsyncViewModel
import com.howettl.mvvm.data.model.User
import com.howettl.mvvm.data.repository.UserRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class UserListViewModel(context: Context) : AsyncViewModel(context) {

    @Inject
    lateinit var userRepository: UserRepository

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadUsers() }

    var users: LiveData<List<User>>? = null

    init {
        loadingVisibility.value = View.GONE
        loadUsers()
    }

    fun loadUsers() {
        if (users == null) {
            users = userRepository.getCachedUsers()
        }

        launch {
            if (userRepository.countCached() == 0) {
                loadingVisibility.value = View.VISIBLE
            }

            try {
                val updatedUsers = userRepository.getRemoteUsers()
                userRepository.insertCached(*updatedUsers.toTypedArray())
            } catch (e: Exception) {
                Timber.e(e)
            }

            loadingVisibility.value = View.GONE
        }
    }
}