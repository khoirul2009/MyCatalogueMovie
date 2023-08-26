package com.mymovie.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.mymovie.core.utils.ArrayConverter


@Entity(tableName = "popular_movie")
data class MovieEntity(
    @ColumnInfo(name = "overview")
    val overview: String,
    @ColumnInfo(name = "originalLanguage")
    val originalLanguage: String,
    @ColumnInfo(name = "originalTitle")
    val originalTitle: String,
    @ColumnInfo(name = "video")
    val video: Boolean,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "genreIds")
    @TypeConverters(ArrayConverter::class)
    val genreIds: String,
    @ColumnInfo(name = "posterPath")
    val posterPath: String?,
    @ColumnInfo(name = "releaseDate")
    val releaseDate: String,
    @ColumnInfo(name = "popularity")
    val popularity: Double,
    @ColumnInfo(name = "voteAverage")
    val voteAverage: Double,
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "adult")
    val adult: Boolean,
    @ColumnInfo(name = "voteCount")
    val voteCount: Int? = 0,
    @ColumnInfo(name = "isBookmark")
    var isBookmark: Boolean = false

)
