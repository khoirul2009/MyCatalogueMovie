package com.mymovie.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mymovie.core.domain.usecase.MovieUseCase

class BookmarksViewModel(movieUseCase: MovieUseCase): ViewModel() {
    val bookmarks = movieUseCase.getBookmarkMovie().asLiveData()
}