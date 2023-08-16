package com.mymovie.core.ui


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mymovie.core.R
import com.mymovie.core.domain.model.Movie
import com.mymovie.core.databinding.MenuItemBinding

class MovieAdapterPaging : PagingDataAdapter<Movie, MovieAdapterPaging.ViewHolder>(DIFF_CALLBACK) {
    private val listData = ArrayList<Movie>()
    var onItemClick: ((Movie) -> Unit)? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {
        private val binding = MenuItemBinding.bind(itemView)
        fun bind(data: Movie) {
            with(binding) {
                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/w300/${data.posterPath}")
                    .into(moviePoster)
                tvMovieTitle.text = data.title
                tvRating.text = itemView.context.getString(R.string.rating, data.voteAverage)
            }
            itemView.setOnClickListener {
                onItemClick?.invoke(data)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.menu_item, parent, false))

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        if(data != null) {
            holder.bind(data)
        }
    }

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