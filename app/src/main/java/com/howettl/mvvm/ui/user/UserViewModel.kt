package com.howettl.mvvm.ui.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.howettl.mvvm.data.model.User

class UserViewModel: ViewModel() {

    val name = MutableLiveData<String>()
    val username = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val phone = MutableLiveData<String>()
    val website = MutableLiveData<String>()

    fun bind(user: User) {
        name.value = user.name
        username.value = user.username
        email.value = user.email
        phone.value = user.phone
        website.value = user.website
    }

}