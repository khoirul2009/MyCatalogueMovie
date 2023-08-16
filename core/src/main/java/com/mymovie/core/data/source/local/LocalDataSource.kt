package com.mymovie.core.data.source.local


import com.mymovie.core.data.source.local.entity.BookmarkMovieEntity
import com.mymovie.core.data.source.local.entity.GenreEntity
import com.mymovie.core.data.source.local.entity.MovieEntity
import com.mymovie.core.data.source.local.room.BookmarkMovieDao
import com.mymovie.core.data.source.local.room.GenreDao
import com.mymovie.core.data.source.local.room.MovieDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val movieDao: MovieDao, private val bookmarkMovieDao: BookmarkMovieDao, private val genresDao: GenreDao) {

    fun getPopularMovie() : Flow<List<MovieEntity>> = movieDao.getPopularMovie()

    suspend fun insertMovie(movie: List<MovieEntity>) {
        movieDao.insertMovie(movie)
    }
    suspend fun insertGenres(genres: List<GenreEntity>) {
        genresDao.insertGenre(genres)
    }
    fun getGenres() : Flow<List<GenreEntity>> = genresDao.getAllGenres()

    fun getBookmarkMovie() : Flow<List<BookmarkMovieEntity>> = bookmarkMovieDao.getBookmarkedMovie()

    fun getBookmarkMovieById(id: Int) : Flow<Int> = bookmarkMovieDao.getBookmarkedMovieById(id)

    fun insertMovieToBookmark(bookmarkMovie: BookmarkMovieEntity) {
        bookmarkMovieDao.addMovieToBookmark(bookmarkMovie)
    }
    fun deleteMovieFromBookmark(bookmarkMovie: BookmarkMovieEntity) {
        bookmarkMovieDao.deleteFromBookmark(bookmarkMovie)
    }

}