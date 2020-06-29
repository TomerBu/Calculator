package edu.tomerbu.tmdbkoin.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.tomerbu.tmdbkoin.data.model.Movie
import edu.tomerbu.tmdbkoin.repository.MovieRepository

class MainViewModel(private val repo: MovieRepository) : ViewModel() {
    private val movies: MutableLiveData<List<Movie>> by lazy {
        refreshMovies()
        MutableLiveData<List<Movie>>()
    }

    private val moviesError = MutableLiveData<Exception>()

    fun getError(): LiveData<java.lang.Exception>{
        return moviesError
    }

    fun getMovies():LiveData<List<Movie>>{
        return movies
    }

    fun refreshMovies(){
        repo.getMovies({
            movies.postValue(it)
        }, {
            moviesError.postValue(it)
        })
    }
}
