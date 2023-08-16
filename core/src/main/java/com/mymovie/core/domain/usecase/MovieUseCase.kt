package com.mymovie.core.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.mymovie.core.data.Resource
import com.mymovie.core.domain.model.*
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getPopularMovie(): Flow<Resource<List<Movie>>>
    fun getBookmarkMovie(): Flow<List<BookmarkMovie>>
    fun insertMovieToBookmark(bookmarkMovie: BookmarkMovie)
    fun deleteMovieFromBookmark(bookmarkMovie: BookmarkMovie)
    fun getBookmarkMovieById(id: Int): Flow<Boolean>
    fun getGenres(): Flow<Resource<List<Genre>>>
    fun getDetailMovie(id: Int): Flow<Resource<DetailMovie>>
    fun getMovie(genres: String?): LiveData<PagingData<Movie>>
    fun searchMovie(query: String): Flow<PagingData<Movie>>
}