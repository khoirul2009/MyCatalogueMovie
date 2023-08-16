package com.mymovie.core.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mymovie.core.data.source.remote.RemoteDataSource
import com.mymovie.core.data.source.remote.network.ApiResponse
import com.mymovie.core.domain.model.Movie
import com.mymovie.core.utils.DataMapper
import kotlinx.coroutines.flow.first

class SearchMoviePagingSource(private val remoteDataSource: RemoteDataSource, private val query: String): PagingSource<Int, Movie>() {
    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val responseData = remoteDataSource.searchMovie(query, page).first()
            lateinit var data: List<Movie>
            responseData.let {
                when(it) {
                    is ApiResponse.Error -> {
                        return LoadResult.Error(Exception(it.errorMessage))
                    }
                    is ApiResponse.Success -> {
                        data = DataMapper.mapMovieResponseToDomain(it.data)
                        return LoadResult.Page(
                            data = data,
                            prevKey = if (page == 1) null else page - 1,
                            nextKey = if (data.isNullOrEmpty()) null else page + 1
                        )
                    }
                    is ApiResponse.Empty -> {
                        Log.e("MoviePagingSource", "Data is Empety")
                        return LoadResult.Error(Exception("Data is Empety"))
                    }
                }
            }

        } catch (e: Exception) {
            Log.e("MoviePagingSource", e.toString())
            return LoadResult.Error(e)
        }

    }
}