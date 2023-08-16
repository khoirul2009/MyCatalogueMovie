package com.mymovie.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarked_movie")
data class BookmarkMovieEntity(
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "posterPath")
    val posterPath: String?,
    @ColumnInfo(name = "popularity")
    val popularity: Double,
    @ColumnInfo(name = "voteAverage")
    val voteAverage: Double,
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
)
