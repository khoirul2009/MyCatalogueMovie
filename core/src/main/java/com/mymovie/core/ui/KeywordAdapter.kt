package com.mymovie.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mymovie.core.R
import com.mymovie.core.domain.model.Movie
import com.mymovie.core.databinding.KeywordItemBinding


class KeywordAdapter : PagingDataAdapter<Movie, KeywordAdapter.ViewHolder>(DIFF_CALLBACK) {
    var onItemClick: ((Movie) -> Unit)? = null
    
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = KeywordItemBinding.bind(itemView)
        fun bind(data: Movie) {
            with(binding) {
                tvName.text = data.title
            }
            itemView.setOnClickListener {
                onItemClick?.invoke(data)
            }
        }


    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position = position)
        if(data != null) {
            holder.bind(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.keyword_item, parent, false))

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }



}