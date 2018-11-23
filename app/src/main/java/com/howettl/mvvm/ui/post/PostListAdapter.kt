package com.howettl.mvvm.ui.post

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.howettl.mvvm.R
import com.howettl.mvvm.data.Post
import com.howettl.mvvm.databinding.ItemPostBinding

class PostListAdapter: RecyclerView.Adapter<PostListAdapter.ViewHolder>() {

    var postList: List<Post> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_post, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(postList[position])
    }

    override fun getItemCount() = postList.size

    inner class ViewHolder(private val binding: ItemPostBinding): RecyclerView.ViewHolder(binding.root) {
        private val viewModel = PostViewModel()

        fun bind(post: Post) {
            viewModel.bind(post)
            binding.viewModel = viewModel
        }
    }

}