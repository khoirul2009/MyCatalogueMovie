package com.mymovie.detail



import androidx.lifecycle.*
import com.mymovie.core.domain.model.DetailMovie
import com.mymovie.core.domain.usecase.MovieUseCase
import com.mymovie.core.utils.DataMapper
import kotlinx.coroutines.flow.map


class DetailViewModel(private val movieUseCase: MovieUseCase): ViewModel() {


    fun getDetailMovie(id: Int) = movieUseCase.getDetailMovie(id).asLiveData()

    fun setBookmarks(movie: DetailMovie) {
        movieUseCase.insertMovieToBookmark(DataMapper.mapDetailMovieToBookmarkMovie(movie))
    }
    fun deleteFromBookmark(movie: DetailMovie) {
        movieUseCase.deleteMovieFromBookmark(DataMapper.mapDetailMovieToBookmarkMovie(movie))
    }

    fun checkBookmarkId(id: Int) =
        movieUseCase.getBookmarkMovieById(id).map {
            it
        }.asLiveData()

}