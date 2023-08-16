package com.mymovie.core.data.source.remote

import android.util.Log
import com.mymovie.core.data.source.remote.network.ApiResponse
import com.mymovie.core.data.source.remote.network.ApiService
import com.mymovie.core.data.source.remote.response.DetailMovieResponse
import com.mymovie.core.data.source.remote.response.GenresItem
import com.mymovie.core.data.source.remote.response.MovieItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService){
    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

    }

    fun getPopularMovie(): Flow<ApiResponse<List<MovieItem>>> {
        return flow {
            try {
                val response = apiService.getPopularMovie()
                val dataArray = response.results
                if(dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getDetailMovie(id: Int): Flow<ApiResponse<DetailMovieResponse>> {
        return flow {
            try {
                val response = apiService.getDetailMovie(id)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getGenres() : Flow<ApiResponse<List<GenresItem>>> {
        return flow {
            try {
                val response = apiService.getGenres()
                val dataArray = response.genres
                if(dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getMovie(genres: String?, page: Int): Flow<ApiResponse<List<MovieItem>>> = flow {
        try {
            val response = apiService.getMovie(genres, page)
            val dataArray = response.results
            if(dataArray.isNotEmpty()) {
                emit(ApiResponse.Success(dataArray))
            } else {
                emit(ApiResponse.Empty)
            }
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.toString()))
            Log.e("RemoteDataSource", e.toString())
        }
    }.flowOn(Dispatchers.IO)


    fun searchMovie(query: String, page: Int) : Flow<ApiResponse<List<MovieItem>>> = flow {
        try {
            val response = apiService.searchMovie(query = query, page = page)
            val dataArray = response.results
            if(dataArray.isNotEmpty()) {
                emit(ApiResponse.Success(dataArray))
            } else {
                emit(ApiResponse.Empty)
            }
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.toString()))
        }
    }.flowOn(Dispatchers.IO)
}