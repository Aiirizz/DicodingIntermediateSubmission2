package com.dicoding.submission.dicodingintermediatesubmission.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.submission.dicodingintermediatesubmission.activity.DetailActivity
import com.dicoding.submission.dicodingintermediatesubmission.databinding.ListStoryBinding
import com.dicoding.submission.dicodingintermediatesubmission.response.ListStoryResponse

class StoryAdapter : PagingDataAdapter<ListStoryResponse, StoryAdapter.ListViewHolder>(DIFF_CALLBACK) {

    class ListViewHolder(var binding: ListStoryBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data: ListStoryResponse){
            binding.homeName.text = data.name
            binding.homeDesc.text = data.description
            Glide.with(itemView.context)
                .load(data.photoUrl)
                .into(binding.homeImg)

            itemView.setOnClickListener{
                val intent = Intent(it.context, DetailActivity::class.java)
                intent.putExtra("STORY", data)
                it.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ListStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryResponse>() {
            override fun areItemsTheSame(oldItem: ListStoryResponse, newItem: ListStoryResponse): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(
                oldItem: ListStoryResponse,
                newItem: ListStoryResponse
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}