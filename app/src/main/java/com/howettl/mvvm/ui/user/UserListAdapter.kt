package com.howettl.mvvm.ui.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.howettl.mvvm.R
import com.howettl.mvvm.data.model.User
import com.howettl.mvvm.databinding.ItemUserBinding

class UserListAdapter: RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    var userList: List<User> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_user, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(userList[position])

        holder.itemView.setOnClickListener { view ->
            val action = UserListFragmentDirections.actionUserListFragmentToUserDetailFragment()
            action.setUserId(userList[position].id)
            view.findNavController().navigate(action)
        }
    }

    override fun getItemCount() = userList.size

    inner class ViewHolder(private val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root) {
        private val viewModel = UserViewModel()

        fun bind(user: User) {
            viewModel.bind(user)
            binding.viewModel = viewModel
        }
    }

}