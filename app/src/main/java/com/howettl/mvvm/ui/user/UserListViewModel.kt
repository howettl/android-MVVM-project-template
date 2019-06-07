package com.howettl.mvvm.ui.user

import android.content.Context
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.howettl.mvvm.R
import com.howettl.mvvm.base.AsyncViewModel
import com.howettl.mvvm.data.model.User
import com.howettl.mvvm.data.repository.UserLocalRepository
import com.howettl.mvvm.data.repository.UserRemoteRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class UserListViewModel(context: Context): AsyncViewModel(context) {

    @Inject
    lateinit var userRemoteRepository: UserRemoteRepository
    @Inject
    lateinit var userLocalRepository: UserLocalRepository

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
            users = userLocalRepository.getAll()
        }

        launch {
            if (userLocalRepository.count() == 0) {
                loadingVisibility.value = View.VISIBLE
            }

            try {
                val updatedUsers = userRemoteRepository.getUsers()
                userLocalRepository.insert(*updatedUsers.toTypedArray())
            } catch (e: Exception) {
                Timber.e(e)
            }

            loadingVisibility.value = View.GONE
        }
    }
}