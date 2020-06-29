package edu.tomerbu.tmdbkoin.repository


import edu.tomerbu.tmdbkoin.data.api.MovieApiService
import edu.tomerbu.tmdbkoin.data.database.MovieDao
import edu.tomerbu.tmdbkoin.data.model.Movie
import edu.tomerbu.tmdbkoin.data.model.MoviesResponse
import edu.tomerbu.tmdbkoin.di.API_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

/**
 * Connects to the end entity, and exposes functionality to the user.
 */
class MovieRepositoryImpl(
    private val movieApiService: MovieApiService,
    private val movieDao: MovieDao
) : MovieRepository {

    override fun getMovies(onMoviesReceived: (List<Movie>) -> Unit, onError: (Exception) -> Unit) {
        movieApiService.getMovies(API_KEY).enqueue(object : Callback<MoviesResponse> {
            override fun onFailure(call: Call<MoviesResponse>, throwable: Throwable) {
                val savedMovies = movieDao.getSavedMovies()

                /**
                 * If there's no internet connection, default to the cached values.
                 * Otherwise propagate the error.
                 * */
                if (throwable is IOException && savedMovies.isNotEmpty()) {
                    onMoviesReceived(savedMovies)
                } else {
                    onError(Exception())
                }
            }

            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                val movies = response.body()?.movies ?: emptyList()

                if (movies.isNotEmpty()) {
                    GlobalScope.launch(Dispatchers.IO) {
                        movieDao.saveMovies(movies)
                    }
                }
                onMoviesReceived(movies)
            }
        })
    }
}