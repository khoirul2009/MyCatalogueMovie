package com.mymovie.core.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.mymovie.core.data.Resource
import com.mymovie.core.domain.model.*
import com.mymovie.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow

class MovieInteractor(private val movieRepository: IMovieRepository): MovieUseCase {
    override fun getPopularMovie(): Flow<Resource<List<Movie>>> = movieRepository.getPopularMovie()
    override fun getBookmarkMovie(): Flow<List<BookmarkMovie>> = movieRepository.getBookmarkMovie()
    override fun insertMovieToBookmark(bookmarkMovie: BookmarkMovie) = movieRepository.addToBookmark(bookmarkMovie)
    override fun deleteMovieFromBookmark(bookmarkMovie: BookmarkMovie) = movieRepository.deleteFromBookmark(bookmarkMovie)
    override fun getBookmarkMovieById(id: Int): Flow<Boolean> = movieRepository.getBookmarkMovieById(id)
    override fun getGenres(): Flow<Resource<List<Genre>>> = movieRepository.getGenres()
    override fun getDetailMovie(id: Int): Flow<Resource<DetailMovie>> = movieRepository.getDetailMovie(id)
    override fun getMovie(genres: String?): LiveData<PagingData<Movie>> = movieRepository.getMovieDiscover(genres)
    override fun searchMovie(query: String): Flow<PagingData<Movie>> = movieRepository.searchMovie(query)
}