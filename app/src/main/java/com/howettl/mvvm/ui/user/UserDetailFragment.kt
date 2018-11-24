package com.howettl.mvvm.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.howettl.mvvm.R
import com.howettl.mvvm.databinding.FragmentUserDetailBinding
import com.howettl.mvvm.injection.ViewModelFactory

class UserDetailFragment: Fragment() {

    private lateinit var binding: FragmentUserDetailBinding
    private lateinit var viewModel: UserDetailViewModel
    private var errorSnackbar: Snackbar? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_detail, container, false)
        viewModel = ViewModelProviders.of(this, ViewModelFactory(context ?: return null)).get(UserDetailViewModel::class.java)
        binding.detailViewModel = viewModel
        binding.userViewModel = viewModel.userViewModel
        binding.setLifecycleOwner(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId = UserDetailFragmentArgs.fromBundle(arguments).userId
        viewModel.loadUser(userId)
    }

    private fun showError(@StringRes message: Int) {
        errorSnackbar = Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
        errorSnackbar?.show()
    }

    private fun hideError() {
        errorSnackbar?.dismiss()
    }
}