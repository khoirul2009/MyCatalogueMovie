package com.mymovie.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mymovie.core.ui.KeywordAdapter
import com.mymovie.core.ui.LoadingStateAdapter
import com.mymovie.core.ui.MovieAdapterPaging
import com.mymovie.databinding.FragmentSearchBinding
import com.mymovie.detail.DetailActivity
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val keywordAdapter = KeywordAdapter()
        val movieAdapter = MovieAdapterPaging()



        if(activity != null) {
            movieAdapter.onItemClick = {
                val intent = Intent(requireContext(), DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_DATA, it.id)
                requireContext().startActivity(intent)
            }

            keywordAdapter.onItemClick = {
                val intent = Intent(requireContext(), DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_DATA, it.id)
                requireContext().startActivity(intent)
            }
            binding.searchView.editText
                .addTextChangedListener(object: TextWatcher {
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        //
                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        lifecycleScope.launch {
                            viewModel.queryChannel.value = p0.toString()
                        }

                    }

                    override fun afterTextChanged(p0: Editable?) {
                       //
                    }
                })
            binding.searchView.editText
                .setOnEditorActionListener { _, _, _ ->
                    val query = binding.searchView.text.toString()
                    viewModel.search(query).observe(viewLifecycleOwner) {
                        movieAdapter.notifyDataSetChanged()
                        movieAdapter.submitData(lifecycle, it)
                    }
                    binding.searchBar.text = query
                    binding.searchView.hide()
                    false
                }
        }

        viewModel.searchResult.observe(viewLifecycleOwner) {
            keywordAdapter.notifyDataSetChanged()
            keywordAdapter.submitData(lifecycle, it)
        }


        with(binding.rvSearchSugest) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = keywordAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    keywordAdapter.retry()
                }
            )
        }
        with(binding.rvSearchItem) {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = movieAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    movieAdapter.retry()
                }
            )
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}