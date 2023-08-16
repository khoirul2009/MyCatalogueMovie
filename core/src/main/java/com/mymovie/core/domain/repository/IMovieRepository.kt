package com.mymovie.core.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.mymovie.core.data.Resource
import com.mymovie.core.domain.model.BookmarkMovie
import com.mymovie.core.domain.model.DetailMovie
import com.mymovie.core.domain.model.Genre
import com.mymovie.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getPopularMovie() : Flow<Resource<List<Movie>>>
    fun getDetailMovie(id: Int) : Flow<Resource<DetailMovie>>
    fun getBookmarkMovie(): Flow<List<BookmarkMovie>>
    fun getBookmarkMovieById(id: Int): Flow<Boolean>
    fun getGenres(): Flow<Resource<List<Genre>>>
    fun addToBookmark(bookmarkMovie: BookmarkMovie)
    fun deleteFromBookmark(bookmarkMovie: BookmarkMovie)
    fun getMovieDiscover(genres: String?): LiveData<PagingData<Movie>>
    fun searchMovie(query: String): Flow<PagingData<Movie>>
}