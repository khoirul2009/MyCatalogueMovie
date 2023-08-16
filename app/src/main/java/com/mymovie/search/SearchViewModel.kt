package com.mymovie.search


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mymovie.core.domain.model.Movie
import com.mymovie.core.domain.usecase.MovieUseCase
import kotlinx.coroutines.flow.*

class SearchViewModel(private val movieUseCase: MovieUseCase): ViewModel() {

    val queryChannel = MutableStateFlow("")

    val searchResult: LiveData<PagingData<Movie>> = queryChannel
        .debounce(300)
        .distinctUntilChanged()
        .filter {
            it.trim().isNotEmpty()
        }
        .mapLatest {
            movieUseCase.searchMovie(it).first()
        }.asLiveData().cachedIn(viewModelScope)

    fun search(query: String): LiveData<PagingData<Movie>> = movieUseCase.searchMovie(query).asLiveData().cachedIn(viewModelScope)


}