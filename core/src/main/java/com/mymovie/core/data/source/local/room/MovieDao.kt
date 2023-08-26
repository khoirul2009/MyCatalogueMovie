package com.mymovie.core.data.source.local.room

import androidx.room.*
import com.mymovie.core.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM popular_movie ORDER BY popularity, voteAverage, voteCount DESC")
    fun getPopularMovie(): Flow<List<MovieEntity>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movies: List<MovieEntity>)

    @Update
    fun updateBookmarkMovie(movieEntity: MovieEntity)
}