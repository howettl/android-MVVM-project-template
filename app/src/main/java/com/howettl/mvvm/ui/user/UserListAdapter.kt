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

    var userList: List<User>? = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_user, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList?.get(position) ?: return
        holder.bind(user)

        holder.itemView.setOnClickListener { view ->
            val action = UserListFragmentDirections.actionUserListFragmentToUserDetailFragment()
            action.setUserId(user.id)
            view.findNavController().navigate(action)
        }
    }

    override fun getItemCount() = userList?.size ?: 0

    inner class ViewHolder(private val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.user = user
        }
    }

}