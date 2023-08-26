package com.mymovie.core.di

import androidx.room.Room
import com.mymovie.core.data.MovieRepository
import com.mymovie.core.data.source.local.LocalDataSource
import com.mymovie.core.data.source.local.room.MovieDatabase
import com.mymovie.core.data.source.remote.RemoteDataSource
import com.mymovie.core.data.source.remote.network.ApiService
import com.mymovie.core.data.source.remote.network.QueryParamsInterceptor
import com.mymovie.core.domain.repository.IMovieRepository
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val databaseModule = module {
    factory { get<MovieDatabase>().movieDao() }
    factory { get<MovieDatabase>().bookmarkMovieDao() }
    factory { get<MovieDatabase>().genreDao() }
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("mymovie".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            MovieDatabase::class.java, "Movie.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {
    single {
        val hostname = "*.themoviedb.org"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/5VLcahb6x4EvvFrCF2TePZulWqrLHS2jCg9Ywv6JHog=")
            .build()
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(QueryParamsInterceptor(androidContext()))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get(), get(), get()) }
    single { RemoteDataSource(get()) }
    single<IMovieRepository> { MovieRepository(get(), get()) }
}