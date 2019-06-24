package com.howettl.mvvm.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.howettl.mvvm.R
import com.howettl.mvvm.databinding.FragmentUserDetailBinding
import com.howettl.mvvm.injection.ViewModelFactory
import com.howettl.mvvm.ui.post.PostAdapter

class UserDetailFragment: Fragment() {

    private lateinit var binding: FragmentUserDetailBinding
    private lateinit var viewModel: UserDetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_detail, container, false)
        viewModel = ViewModelProviders.of(this, ViewModelFactory(context ?: return null)).get(UserDetailViewModel::class.java)
        binding.detailViewModel = viewModel
        binding.postsRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.setLifecycleOwner(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId = UserDetailFragmentArgs.fromBundle(arguments ?: return).userId
        viewModel.loadUser(userId)

        val adapter = PostAdapter()
        binding.postAdapter = adapter
        viewModel.posts?.observe(this, Observer {
            adapter.posts = it
        })

        viewModel.user?.observe(this, Observer {
            binding.user = it
        })
    }
}