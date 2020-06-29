package edu.tomerbu.tmdbkoin.repository

import edu.tomerbu.tmdbkoin.data.model.Movie


/**
 * Interface used to communicate to the end entities, when fetching data.
 */
interface MovieRepository {

  fun getMovies(
      onMoviesReceived: (List<Movie>) -> Unit,
      onError: (Exception) -> Unit
  )
}