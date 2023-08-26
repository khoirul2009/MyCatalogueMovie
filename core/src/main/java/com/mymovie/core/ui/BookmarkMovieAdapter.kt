package com.mymovie.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mymovie.core.R
import com.mymovie.core.domain.model.BookmarkMovie
import com.mymovie.core.databinding.BookmarkItemBinding
import com.mymovie.core.utils.DiffCallbackBookmark

class BookmarkMovieAdapter : RecyclerView.Adapter<BookmarkMovieAdapter.ViewHolder>() {
    private val listData = ArrayList<BookmarkMovie>()
    var onItemClick: ((BookmarkMovie) -> Unit)? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = BookmarkItemBinding.bind(itemView)
        fun bind(data: BookmarkMovie) {
            with(binding) {
                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/w300/${data.posterPath}")
                    .into(image)
                tvTitle.text = data.title
                tvRating.text = itemView.context.getString(R.string.rating, data.voteAverage)
            }
            itemView.setOnClickListener {
                onItemClick?.invoke(data)
            }
        }

    }

    fun setData(newListData: List<BookmarkMovie>?) {
        if (newListData == null) return
        val diffCalback = DiffCallbackBookmark(listData, newListData)
        val diffResult = DiffUtil.calculateDiff(diffCalback)
        listData.clear()
        listData.addAll(newListData)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.bookmark_item, parent, false)
        )

    override fun getItemCount(): Int = listData.size
}