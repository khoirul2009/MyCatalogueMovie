package com.mymovie.bookmarks

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val bookmarkModule = module {
    viewModel { BookmarksViewModel(get()) }
}