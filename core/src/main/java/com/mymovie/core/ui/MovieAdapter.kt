package com.mymovie.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.marginEnd
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mymovie.core.R
import com.mymovie.core.databinding.MenuItemBinding
import com.mymovie.core.databinding.MovieItemBinding
import com.mymovie.core.domain.model.Movie


class MovieAdapter : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    private val listData = ArrayList<Movie>()
    var onItemClick: ((Movie) -> Unit)? = null

    fun setData(newListData: List<Movie>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {
        private val binding = MovieItemBinding.bind(itemView)
        fun bind(data: Movie) {
            val rating = data.voteAverage / 2
            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w300/${data.posterPath}")
                .into(binding.moviePoster)
            binding.movieTitle.text = data.title

            binding.starContainer.removeAllViews()

            for (i in 1..rating.toInt()) {
                val starImage = ImageView(binding.root.context)
                starImage.setImageResource(R.drawable.baseline_star_24)
                starImage.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                binding.starContainer.addView(starImage)
            }

            if (rating - rating.toInt() > 0) {
                val halfStarImage = ImageView(binding.root.context)
                halfStarImage.setImageResource(R.drawable.baseline_star_half_24)
                halfStarImage.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                binding.starContainer.addView(halfStarImage)
            }
            itemView.setOnClickListener {
                onItemClick?.invoke(data)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false))

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }
}