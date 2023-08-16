package com.mymovie.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mymovie.core.data.source.local.entity.BookmarkMovieEntity
import com.mymovie.core.data.source.local.entity.GenreEntity
import com.mymovie.core.data.source.local.entity.MovieEntity


@Database(entities = [MovieEntity::class, BookmarkMovieEntity::class, GenreEntity::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun bookmarkMovieDao(): BookmarkMovieDao
    abstract fun genreDao(): GenreDao
}