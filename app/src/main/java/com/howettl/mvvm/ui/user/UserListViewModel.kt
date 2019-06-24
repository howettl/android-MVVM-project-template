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

    val users: LiveData<List<User>> = userRepository.getUsers()

    init {
        loadingVisibility.value = View.GONE
        loadUsers()
    }

    fun loadUsers() {
        launch {
            if (userRepository.getCachedCount() == 0) {
                loadingVisibility.value = View.VISIBLE
            }

            userRepository.refreshUsers()

            loadingVisibility.value = View.GONE
        }
    }
}