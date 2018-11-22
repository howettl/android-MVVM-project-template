package com.howettl.mvvm.ui.post

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.howettl.mvvm.databinding.ItemPostBinding
import com.howettl.mvvm.R
import com.howettl.mvvm.model.Post

class PostListAdapter: RecyclerView.Adapter<PostListAdapter.ViewHolder>() {

    private lateinit var postList: List<Post>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_post, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(postList[position])
    }

    override fun getItemCount() = if(::postList.isInitialized) postList.size else 0

    fun updatePostList(postList: List<Post>) {
        this.postList = postList
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemPostBinding): RecyclerView.ViewHolder(binding.root) {
        private val viewModel = PostViewModel()

        fun bind(post: Post) {
            viewModel.bind(post)
            binding.viewModel = viewModel
        }
    }

}