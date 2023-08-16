package com.mymovie.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class BookmarkMovie(
    val title: String,
    val posterPath: String?,
    val popularity: Double,
    val voteAverage: Double,
    val id: Int,
): Parcelable
