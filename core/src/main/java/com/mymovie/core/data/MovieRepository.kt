package com.mymovie.core.data


import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.mymovie.core.data.source.local.LocalDataSource
import com.mymovie.core.data.source.local.entity.GenreEntity
import com.mymovie.core.data.source.remote.RemoteDataSource
import com.mymovie.core.data.source.remote.network.ApiResponse
import com.mymovie.core.data.source.remote.response.GenresItem
import com.mymovie.core.data.source.remote.response.MovieItem
import com.mymovie.core.domain.model.BookmarkMovie
import com.mymovie.core.domain.model.DetailMovie
import com.mymovie.core.domain.model.Genre
import com.mymovie.core.domain.model.Movie
import com.mymovie.core.domain.repository.IMovieRepository
import com.mymovie.core.utils.AppExecutors
import com.mymovie.core.utils.DataMapper
import kotlinx.coroutines.flow.*

class MovieRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors

) : IMovieRepository {

    override fun getPopularMovie(): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieItem>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getPopularMovie().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override suspend fun createCall(): Flow<ApiResponse<List<MovieItem>>> =
                remoteDataSource.getPopularMovie()


            override suspend fun saveCallResult(data: List<MovieItem>) {
                val movieList = DataMapper.mapResponseToEntities(data)
                localDataSource.insertMovie(movieList)
            }

            override fun shouldFetch(data: List<Movie>?): Boolean = true

        }.asFlow()

    override fun getDetailMovie(id: Int): Flow<Resource<DetailMovie>> {
        return  flow {
            emit(Resource.Loading())
            val data = remoteDataSource.getDetailMovie(id).first()
            data.let {
                when(it) {
                    is ApiResponse.Success -> {
                        emit(Resource.Success(
                            DetailMovie(
                                originalLanguage = it.data.originalLanguage,
                                imdbId = it.data.imdbId,
                                video = it.data.video,
                                title = it.data.title,
                                revenue = it.data.revenue,
                                id = it.data.id,
                                popularity = it.data.popularity,
                                originalTitle = it.data.originalTitle,
                                adult = it.data.adult,
                                voteCount = it.data.voteCount,
                                voteAverage = it.data.voteAverage,
                                releaseDate = it.data.releaseDate,
                                posterPath = it.data.posterPath,
                                overview = it.data.overview,
                                belongsToCollection = it.data.belongsToCollection,
                                budget = it.data.budget,
                                homepage = it.data.homepage,
                                runtime = it.data.runtime,
                                status = it.data.status,
                                tagline = it.data.tagline
                                )))
                    }
                    is ApiResponse.Error -> {
                        emit(Resource.Error(it.errorMessage))
                    }
                    else -> {
                    }
                }
            }
        }
    }

    override fun getBookmarkMovie(): Flow<List<BookmarkMovie>> {
        return localDataSource.getBookmarkMovie().map {
            DataMapper.mapBookmarkEntityToBookmarkDomain(it)
        }
    }


    override fun getBookmarkMovieById(id: Int): Flow<Boolean>  =
        localDataSource.getBookmarkMovieById(id).map {
            it >= 1
        }

    override fun getGenres(): Flow<Resource<List<Genre>>>  =
        object : NetworkBoundResource<List<Genre>, List<GenresItem>>() {
            override fun loadFromDB(): Flow<List<Genre>> =
                localDataSource.getGenres().map {data ->
                    data.map {
                        Genre(
                            id = it.id,
                            name = it.name
                        )
                    }
                }
            override suspend fun createCall(): Flow<ApiResponse<List<GenresItem>>> =
                remoteDataSource.getGenres()

            override suspend fun saveCallResult(data: List<GenresItem>) {
                val listGenreEntity = ArrayList<GenreEntity>()
                data.map {
                    val genre = GenreEntity(it.name, it.id)
                    listGenreEntity.add(genre)
                }
                localDataSource.insertGenres(listGenreEntity)
            }

            override fun shouldFetch(data: List<Genre>?): Boolean = true
        }.asFlow()

    override fun addToBookmark(bookmarkMovie: BookmarkMovie) {
        appExecutors.diskIO().execute {
            localDataSource.insertMovieToBookmark(
                DataMapper.mapBookmarkDomainToEntity(bookmarkMovie)
            )
        }
    }

    override fun deleteFromBookmark(bookmarkMovie: BookmarkMovie) {
        appExecutors.diskIO().execute {
            localDataSource.deleteMovieFromBookmark(
                DataMapper.mapBookmarkDomainToEntity(bookmarkMovie)
            )

        }
    }

    override fun getMovieDiscover(genres: String?): LiveData<PagingData<Movie>> =
        Pager(
            config = PagingConfig(
                pageSize = 18
            ),
            pagingSourceFactory = {
                MoviePagingSource(remoteDataSource, genres)
            }
        ).liveData


    override fun searchMovie(query: String): Flow<PagingData<Movie>> =
        Pager(
            config = PagingConfig(
                pageSize = 18
            ),
            pagingSourceFactory = {
                SearchMoviePagingSource(remoteDataSource, query)
            }
        ).flow


}