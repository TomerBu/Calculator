package edu.tomerbu.tmdbkoin.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import edu.tomerbu.tmdbkoin.R
import edu.tomerbu.tmdbkoin.data.model.Movie
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.android.viewmodel.ext.android.getViewModel

class MainFragment : Fragment() {
    private val movieAdapter: MovieAdapter by lazy {
        val movieAdapter = MovieAdapter()
        recyclerView.adapter = movieAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        swipeToRefresh.setOnRefreshListener {
            viewModel.refreshMovies()
        }
        return@lazy movieAdapter
    }

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = MovieAdapter()

        //viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel = getViewModel()

        viewModel.getMovies().observe(viewLifecycleOwner, Observer { l ->
            showMovies(l)
        })

        viewModel.getError().observe(viewLifecycleOwner, Observer {
            showError(it)
        })


    }

    private fun showMovies(movies: List<Movie>) {
        movieAdapter.setData(movies)
        swipeToRefresh.isRefreshing = false
        //swipeToRefresh.isRefreshing = false
    }

    private fun showError(e: Exception) {
        val message = if (e.message != null) e.localizedMessage else "Unknown error"
        swipeToRefresh.isRefreshing = false
        Snackbar.make(fab, message , Snackbar.LENGTH_LONG).show()
    }

}
