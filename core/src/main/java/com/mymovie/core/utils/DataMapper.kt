package com.mymovie.core.utils

import com.mymovie.core.data.source.local.entity.BookmarkMovieEntity
import com.mymovie.core.data.source.local.entity.MovieEntity
import com.mymovie.core.data.source.remote.response.MovieItem
import com.mymovie.core.domain.model.BookmarkMovie
import com.mymovie.core.domain.model.DetailMovie
import com.mymovie.core.domain.model.Movie

object DataMapper {
    fun mapResponseToEntities(input: List<MovieItem>): List<MovieEntity> {
        val movieList = ArrayList<MovieEntity>()
        input.map {
            val movie = MovieEntity(
                overview = it.overview,
                originalLanguage = it.originalLanguage,
                originalTitle = it.originalTitle,
                video = it.video,
                title = it.title,
                genreIds = ArrayConverter().fromIntArray(it.genreIds),
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                popularity = it.popularity,
                id = it.id,
                voteAverage = it.voteAverage,
                voteCount = it.voteCount,
                adult = it.adult,
            )
            movieList.add(movie)
        }
        return movieList
    }
    fun mapEntitiesToDomain(input: List<MovieEntity>): List<Movie> =
        input.map {
            Movie(
                overview = it.overview,
                originalLanguage = it.originalLanguage,
                originalTitle = it.originalTitle,
                video = it.video,
                title = it.title,
                genreIds = ArrayConverter().toIntArray(it.genreIds),
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                popularity = it.popularity,
                id = it.id,
                voteAverage = it.voteAverage,
                voteCount = it.voteCount,
                adult = it.adult
            )
        }

    fun mapBookmarkEntityToBookmarkDomain(input: List<BookmarkMovieEntity>): List<BookmarkMovie> =
        input.map { data ->
            BookmarkMovie(
                title = data.title,
                posterPath = data.posterPath,
                voteAverage = data.voteAverage,
                popularity = data.popularity,
                id = data.id
            )
        }
    fun mapBookmarkDomainToEntity(input: BookmarkMovie) = BookmarkMovieEntity(
        title = input.title,
        posterPath = input.posterPath,
        voteAverage = input.voteAverage,
        popularity = input.popularity,
        id = input.id
    )

    fun mapMovieResponseToDomain(data: List<MovieItem>): List<Movie> =
        data.map { input ->
            Movie(
                title = input.title,
                posterPath = input.posterPath,
                voteAverage = input.voteAverage,
                popularity = input.popularity,
                id = input.id,
                overview = input.overview,
                releaseDate = input.releaseDate,
                voteCount = input.voteCount,
                adult = input.adult,
                originalTitle = input.originalTitle,
                video = input.video,
                originalLanguage = input.originalLanguage,
                genreIds = input.genreIds
                )
        }

    fun mapDetailMovieToBookmarkMovie(input: DetailMovie) = BookmarkMovie(
        title = input.title,
        posterPath = input.posterPath,
        voteAverage = input.voteAverage,
        popularity = input.popularity,
        id = input.id
    )


    fun mapDomainToEntity(input: Movie)  = MovieEntity(
            overview = input.overview,
            originalLanguage = input.originalLanguage,
            originalTitle = input.originalTitle,
            video = input.video,
            title = input.title,
            genreIds = ArrayConverter().fromIntArray(input.genreIds),
            posterPath = input.posterPath,
            releaseDate = input.releaseDate,
            popularity = input.popularity,
            id = input.id,
            voteAverage = input.voteAverage,
            voteCount = input.voteCount,
            adult = input.adult,
        )
}

