package com.mymovie.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mymovie.core.data.source.local.entity.BookmarkMovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkMovieDao {
    @Query("SELECT * FROM bookmarked_movie")
    fun getBookmarkedMovie(): Flow<List<BookmarkMovieEntity>>

    @Query("SELECT COUNT(*) FROM bookmarked_movie WHERE id=:id")
    fun getBookmarkedMovieById(id: Int): Flow<Int>

    @Insert
    fun addMovieToBookmark(bookmarkMovie: BookmarkMovieEntity)

    @Delete
    fun deleteFromBookmark(bookmarkMovie: BookmarkMovieEntity)
}