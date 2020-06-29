package edu.tomerbu.tmdbkoin.di

import edu.tomerbu.tmdbkoin.data.api.MovieApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val BASE_URL = "https://api.themoviedb.org/"
const val API_KEY = "b3b1492d3e91e9f9403a2989f3031b0c"
const val MOVIE_IMAGE_BASE_PATH = "https://image.tmdb.org/t/p/w500"

fun networkingModule() = module {
    //get<OkHttpClient>()
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    } // client

    single { GsonConverterFactory.create() } // gson converter
    //get<Retrofit>()
    single {
        Retrofit.Builder()
            .addConverterFactory(get<GsonConverterFactory>())
            .client(get<OkHttpClient>())
            .baseUrl(BASE_URL)
            .build()
    } // retrofit

    //get<MovieApiService>()
    single { get<Retrofit>().create(MovieApiService::class.java) } // api service

}