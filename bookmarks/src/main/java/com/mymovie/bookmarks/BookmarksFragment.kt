package com.mymovie.bookmarks

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mymovie.bookmarks.databinding.FragmentBookmarksBinding
import com.mymovie.core.ui.BookmarkMovieAdapter
import com.mymovie.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

/**
 * A simple [Fragment] subclass.
 * Use the [BookmarksFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BookmarksFragment : Fragment() {

    private val bookmarksViewModel: BookmarksViewModel by viewModel()
    private var _binding: FragmentBookmarksBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookmarksBinding.inflate(inflater, container, false)

        loadKoinModules(bookmarkModule)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if(activity != null) {
            val bookmarkMovieAdapter = BookmarkMovieAdapter()
            bookmarkMovieAdapter.onItemClick = { selectedData ->
                // intent to detail
                val intent = Intent(requireContext(), DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_DATA, selectedData.id)
                startActivity(intent)
            }
            bookmarksViewModel.bookmarks.observe(viewLifecycleOwner, {
                if(it != null) {
                    if(it.size >= 1) {
                        bookmarkMovieAdapter.setData(it)
                    } else {
                        binding.viewEmpty.root.visibility = View.VISIBLE
                        bookmarkMovieAdapter.setData(it)
                    }
                }
            })

            with(binding.rvBookmarksMovie) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = bookmarkMovieAdapter
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}