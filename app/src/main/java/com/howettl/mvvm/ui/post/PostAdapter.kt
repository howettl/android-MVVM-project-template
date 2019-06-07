package com.howettl.mvvm.ui.post

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.howettl.mvvm.R
import com.howettl.mvvm.data.model.Post
import com.howettl.mvvm.databinding.ItemPostBinding

class PostAdapter: RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    var posts: List<Post>? = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_post, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(posts?.get(position) ?: return)

        // TODO potentially add post click listener to do some actions here
    }

    override fun getItemCount() = posts?.size ?: 0

    inner class ViewHolder(private val binding: ItemPostBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            binding.post = post
        }
    }

}