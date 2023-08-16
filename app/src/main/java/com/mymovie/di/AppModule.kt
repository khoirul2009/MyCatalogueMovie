package com.mymovie.di


import com.mymovie.core.domain.usecase.MovieInteractor
import com.mymovie.core.domain.usecase.MovieUseCase
import com.mymovie.detail.DetailViewModel
import com.mymovie.home.HomeViewModel
import com.mymovie.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<MovieUseCase> { MovieInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { SearchViewModel(get()) }
}