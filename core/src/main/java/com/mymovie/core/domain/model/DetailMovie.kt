package com.mymovie.core.domain.model



data class DetailMovie(
    val originalLanguage: String,
    val imdbId: String?,
    val video: Boolean,
    val title: String,
    val revenue: Int,
    val popularity: Double,
    val id: Int,
    val voteCount: Int,
    val budget: Int,
    val overview: String,
    val originalTitle: String,
    val runtime: Int,
    val posterPath: String?,
    val releaseDate: String,
    val voteAverage: Double,
    val belongsToCollection: Any?,
    val tagline: String,
    val adult: Boolean,
    val homepage: String,
    val status: String
)


