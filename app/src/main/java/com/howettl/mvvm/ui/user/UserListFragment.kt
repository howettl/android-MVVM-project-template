package com.howettl.mvvm.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.howettl.mvvm.R
import com.howettl.mvvm.base.InjectedFragment
import com.howettl.mvvm.databinding.FragmentUserListBinding
import com.howettl.mvvm.injection.component.ApplicationComponent

class UserListFragment : InjectedFragment() {

    private lateinit var binding: FragmentUserListBinding
    private lateinit var viewModel: UserListViewModel
    private var errorSnackbar: Snackbar? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_list, container, false)
        binding.userList.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        viewModel = ViewModelProviders.of(this, viewModelFactory)[UserListViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage) else hideError()
        })

        val adapter = UserListAdapter()
        viewModel.users.observe(this, Observer {
            adapter.userList = it
        })
        binding.adapter = adapter
    }

    override fun onResume() {
        super.onResume()

        viewModel.loadUsers()
    }

    private fun showError(@StringRes message: Int) {
        errorSnackbar = Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.retry, viewModel.errorClickListener)
        errorSnackbar?.show()
    }

    private fun hideError() {
        errorSnackbar?.dismiss()
    }

    override fun inject(component: ApplicationComponent) {
        component.inject(this)
    }
}