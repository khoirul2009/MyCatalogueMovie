package com.mymovie.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mymovie.core.domain.usecase.MovieUseCase
import com.mymovie.core.utils.ArrayConverter

class HomeViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {
    private val _selectedGenres = MutableLiveData<String>()
    val selectedGenres : LiveData<String> = _selectedGenres
    fun addGenres(genreId: List<Int>) {
        _selectedGenres.value = ArrayConverter().fromIntArray(genreId)
    }
    val movie = movieUseCase.getPopularMovie().asLiveData()
    val genres = movieUseCase.getGenres().asLiveData()
    fun getMovieDiscover() = movieUseCase.getMovie(_selectedGenres.value)
}