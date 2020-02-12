package com.nataliya.moviedb.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nataliya.moviedb.R
import com.nataliya.moviedb.model.Movie
import com.nataliya.moviedb.recycler.adapter.MovieAdapter
import com.nataliya.moviedb.state.State
import com.nataliya.moviedb.viewmodel.MoviesViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MoviesListActivity : AppCompatActivity(), MovieAdapter.OnMovieClickListener {

    private lateinit var viewModel: MoviesViewModel

    private var adapter: MovieAdapter? = null
    private var movies: ArrayList<Movie> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewModel()
        initAdapter()
        initListener()
    }

    override fun onResume() {
        super.onResume()
        viewModel.refresh()
    }

    private fun setState(state: State) {
        when (state) {
            is State.Loaded -> {
                rvMovies.visibility = View.VISIBLE
                emptyItem.visibility = View.GONE
            }
            is State.Error -> {
                rvMovies.visibility = View.GONE
                emptyItem.visibility = View.VISIBLE
            }
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)

        viewModel.movies.observe(this, Observer {
            addItems(it)
        })

        viewModel.state.observe(this, Observer {
            setState(it)
        })
    }

    private fun addItems(list: List<Movie>) {
        val linkedHashSet: LinkedHashSet<Movie> = LinkedHashSet()
        linkedHashSet.addAll(movies)
        linkedHashSet.addAll(list)

        movies.clear()
        movies.addAll(linkedHashSet)

        adapter?.notifyDataSetChanged()
    }

    private fun initAdapter() {
        adapter = MovieAdapter(movies, this)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.isSmoothScrollbarEnabled = false

        rvMovies.adapter = adapter
        rvMovies.layoutManager = layoutManager
        rvMovies.isNestedScrollingEnabled = false
        rvMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val visibleItemCount: Int = layoutManager.childCount
                    val totalItemCount: Int = layoutManager.itemCount
                    val firstVisibleItemPosition: Int = layoutManager.findFirstVisibleItemPosition()
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount) {
                        viewModel.getPopularMovies()
                    }
                }
            }
        })
    }

    private fun initListener() {
        btnFavorite.setOnClickListener {
            val intent = Intent(this, FavoriteMoviesActivity::class.java)
            startActivityForResult(intent, 0x200)
        }

        btnUpdate.setOnClickListener { viewModel.refresh() }
    }

    override fun onMovieClick(id: Int) {
        val intent = Intent(this, MovieActivity::class.java)
        intent.putExtra(MovieActivity.MOVIE_ID, id)
        startActivityForResult(intent, 0x100)
    }
}
