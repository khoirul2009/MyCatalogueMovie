package com.mymovie.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.mymovie.core.R
import com.mymovie.core.data.Resource
import com.mymovie.databinding.ActivityDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailActivity : AppCompatActivity() {
    private val viewModel: DetailViewModel by viewModel()
    private lateinit var binding: ActivityDetailBinding
    private var isBookmarked: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }

        intent.getIntExtra(EXTRA_DATA, 0).let { id ->
            viewModel.checkBookmarkId(id).observe(this) {
                setStatusBookmarks(it)
                isBookmarked = it
            }

            viewModel.getDetailMovie(id).observe(this) { movie ->
                if (movie != null) {
                    when (movie) {
                        is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                        is Resource.Success -> {
                            binding.progressBar.visibility = View.GONE
                            Glide.with(this)
                                .load("https://image.tmdb.org/t/p/w300/${movie.data?.posterPath}")
                                .into(binding.posterBanner)
                            binding.tvMovieTitle.text = movie.data?.title
                            binding.tvRating.text =
                                getString(R.string.rating, movie.data?.voteAverage)
                            binding.tvYear.text = getString(
                                R.string.year_format,
                                movie.data?.releaseDate!!.split("-")[0]
                            )
                            binding.tvTagline.text = movie.data?.tagline
                            binding.tvStatus.text = movie.data?.status
                            binding.tvOverview.text = movie.data?.overview
                            binding.fabBookmark.setOnClickListener {
                                movie.data.let {
                                    if (isBookmarked) {
                                        viewModel.deleteFromBookmark(movie.data!!)
                                    } else {
                                        viewModel.setBookmarks(movie.data!!)
                                    }
                                    viewModel.checkBookmarkId(id)
                                }
                            }
                        }
                        is Resource.Error -> {
                            binding.progressBar.visibility = View.GONE
                            binding.viewError.root.visibility = View.VISIBLE
                            binding.viewError.tvError.text =
                                movie.message ?: getString(R.string.something_wrong)
                        }
                    }
                }
            }

        }
    }

    private fun setStatusBookmarks(statusBookmarks: Boolean) {
        if(statusBookmarks) {
            binding.fabBookmark.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_bookmarks))
        } else {
            binding.fabBookmark.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_not_bookmark))
        }
    }

    companion object {
        const val EXTRA_DATA: String = "extra_id"
    }
}