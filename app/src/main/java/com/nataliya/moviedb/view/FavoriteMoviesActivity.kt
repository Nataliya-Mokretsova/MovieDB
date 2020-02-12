package com.nataliya.moviedb.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nataliya.moviedb.R
import com.nataliya.moviedb.recycler.adapter.MovieAdapter
import com.nataliya.moviedb.viewmodel.MoviesViewModel
import kotlinx.android.synthetic.main.activity_favorite_movies.*

class FavoriteMoviesActivity : AppCompatActivity(), MovieAdapter.OnMovieClickListener {

    private lateinit var viewModel: MoviesViewModel
    private var adapter: MovieAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_movies)

        initViewModel()
        initListeners()
        initAdapter()
    }

    override fun onResume() {
        super.onResume()
        initList()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)
    }

    private fun initAdapter() {
        adapter = MovieAdapter(ArrayList(), this)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.isSmoothScrollbarEnabled = false

        rvMovies.adapter = adapter
        rvMovies.layoutManager = layoutManager
        rvMovies.isNestedScrollingEnabled = false
    }

    private fun initList() {
        adapter?.setList(ArrayList(viewModel.getFavoriteMovies()))

        if (viewModel.getFavoriteMovies().isEmpty()) {
            emptyItem.visibility = View.VISIBLE
            rvMovies.visibility = View.GONE
        } else {
            emptyItem.visibility = View.GONE
            rvMovies.visibility = View.VISIBLE
        }
    }

    private fun initListeners() {
        ivBack.setOnClickListener { onBackPressed() }
    }

    override fun onMovieClick(id: Int) {
        val intent = Intent(this, MovieActivity::class.java)
        intent.putExtra(MovieActivity.MOVIE_ID, id)
        startActivityForResult(intent, 0x100)
    }
}
