package com.mymovie.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.mymovie.R
import com.mymovie.core.data.Resource
import com.mymovie.core.domain.model.Genre
import com.mymovie.core.ui.LoadingStateAdapter
import com.mymovie.core.ui.MovieAdapter
import com.mymovie.databinding.FragmentHomeBinding
import com.mymovie.detail.DetailActivity
import com.mymovie.core.ui.MovieAdapterPaging
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {
    private  val homeViewModel: HomeViewModel by viewModel()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(activity != null) {
            val movieAdapter = MovieAdapter()

            movieAdapter.onItemClick = { selectedData ->
                // intent to detail
                val intent = Intent(requireContext(), DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_DATA, selectedData.id)
                startActivity(intent)
            }

            homeViewModel.movie.observe(viewLifecycleOwner, {movie ->
                if(movie != null) {
                    when(movie) {
                        is Resource.Loading -> binding.preloaderLayout.visibility = View.VISIBLE
                        is Resource.Success -> {
//                            binding.progressBar.visibility = View.GONE
                            binding.preloaderLayout.visibility = View.GONE
                            movieAdapter.setData(movie.data)
                        }
                        is Resource.Error -> {
//                            binding.progressBar.visibility = View.GONE
                            binding.preloaderLayout.visibility = View.GONE
                            binding.viewError.root.visibility = View.VISIBLE
                            binding.viewError.tvError.text = movie.message ?: getString(R.string.something_wrong)
                        }
                    }
                }
            })


            homeViewModel.genres.observe(viewLifecycleOwner, {

                if(it != null) {
                    when(it) {
                        is Resource.Loading -> binding.preloaderLayout.visibility = View.VISIBLE
                        is Resource.Success -> {
                            setDataChip(it.data)
                            binding.genreList.visibility = View.VISIBLE
                            binding.preloaderLayout.visibility = View.GONE
                        }
                        is Resource.Error -> {
                            binding.preloaderLayout.visibility = View.GONE
                            Toast.makeText(requireActivity(), "Someting Error", Toast.LENGTH_SHORT).show()
                        }

                    }
                }
            })

            with(binding.rvMovie) {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
                adapter = movieAdapter
            }

            val movieAdapterDiscover = MovieAdapterPaging()

            movieAdapterDiscover.onItemClick = {
                val intent = Intent(requireContext(), DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_DATA, it.id)
                requireContext().startActivity(intent)
            }


            with(binding.discoverMovie) {
                layoutManager = GridLayoutManager(context, 2)
                adapter = movieAdapterDiscover.withLoadStateFooter(
                    footer = LoadingStateAdapter {
                        movieAdapterDiscover.retry()
                    }
                )
            }

            loadDataMovie(movieAdapterDiscover)

            homeViewModel.selectedGenres.observe(viewLifecycleOwner, {
                loadDataMovie(movieAdapterDiscover)
            })

        }
        binding.genreList.isHorizontalScrollBarEnabled = false
    }

    private fun loadDataMovie(movieAdapterDiscover: MovieAdapterPaging) {
        homeViewModel.getMovieDiscover().observe(viewLifecycleOwner, {
            movieAdapterDiscover.submitData(lifecycle, it)
        })
    }



    private fun setDataChip(genres: List<Genre>?) {

        val selectedGenre = ArrayList<Int>()
        genres?.map {
            val chip = Chip(requireContext())
            chip.text = it.name
            chip.isClickable= true
            chip.isCheckable = true
            chip.setOnCheckedChangeListener {compoundButton, isChecked ->
                if (isChecked) {
                    selectedGenre.add(it.id)
                } else {
                    selectedGenre.remove(it.id)
                }
                homeViewModel.addGenres(selectedGenre.distinct())
            }
            binding.chipGroup.addView(chip)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}