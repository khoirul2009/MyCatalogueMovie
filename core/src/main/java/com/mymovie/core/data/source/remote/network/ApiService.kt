package com.mymovie.core.data.source.remote.network

import com.mymovie.core.data.source.remote.response.DetailMovieResponse
import com.mymovie.core.data.source.remote.response.GenreResponse
import com.mymovie.core.data.source.remote.response.MovieResponse
import com.mymovie.core.data.source.remote.response.PopularMovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    suspend fun getPopularMovie() : PopularMovieResponse

    @GET("movie/{id}")
    suspend fun getDetailMovie(
        @Path("id") id: Int
    ) : DetailMovieResponse

    @GET("genre/movie/list")
    suspend fun getGenres() : GenreResponse

    @GET("discover/movie")
    suspend fun getMovie(
        @Query("with_genres") genre: String?,
        @Query("page") page: Int
    ) : MovieResponse


    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") query: String,
        @Query("page") page: Int
    ) : MovieResponse

}
