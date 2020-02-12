package com.nataliya.moviedb.view

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.nataliya.moviedb.R
import com.nataliya.moviedb.model.Movie
import com.nataliya.moviedb.model.MovieGenre
import com.nataliya.moviedb.recycler.adapter.GenreAdapter
import com.nataliya.moviedb.state.State
import com.nataliya.moviedb.viewmodel.MoviesViewModel
import kotlinx.android.synthetic.main.activity_movie.*


class MovieActivity : AppCompatActivity() {

    companion object {
        const val MOVIE_ID = "movie_id"
    }

    private lateinit var viewModel: MoviesViewModel

    private var movieID: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        initArguments()
        initListeners()
        initViewModel()
    }

    private fun setState(state: State) {
        when (state) {
            is State.Error -> {
                val movie = viewModel.getMovieFromCache(movieID)
                if (movie != null) {
                    initMovieInfo(movie)
                } else {
                    emptyItem.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun initArguments() {
        if (intent != null) {
            movieID = intent.getIntExtra(MOVIE_ID, 0)
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)

        viewModel.movie.observe(this, Observer {
            initMovieInfo(it)
        })

        viewModel.state.observe(this, Observer {
            setState(it)
        })

        loadMovie()
    }

    private fun loadMovie() {
        viewModel.getMovie(movieID)
    }

    private fun initListeners() {
        ivBack.setOnClickListener { onBackPressed() }

        btnUpdate.setOnClickListener { loadMovie() }
    }

    private fun initMovieInfo(data: Movie) {
        emptyItem.visibility = View.GONE

        tvName.text = data.title

        val date = "Release Date: ${data.getReleaseDate(data.releaseDate)}"
        tvReleaseDate.text = date

        tvName.text = data.title
        tvOverview.text = data.overview
        tvRating.text = data.voteAverage.toString()

        val stars = if (data.voteAverage != 0.0f) 0.5f * (1 + data.voteAverage) else 0f
        ratingBar.rating = stars
        ratingBar.visibility = View.VISIBLE

        val options =
            RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)
                .transform(RoundedCorners(16))

        Glide.with(ivPicture.context)
            .load(data.getPosterPath(data.posterPath))
            .apply(options)
            .transition(DrawableTransitionOptions().crossFade())
            .into(ivPicture)

        Glide.with(ivBackground.context)
            .load(data.getBackDropPath(data.backDropPath))
            .apply(options)
            .transition(DrawableTransitionOptions().crossFade())
            .into(ivBackground)

        initGenreAdapter(data.genres)

        isFavorite.isChecked = viewModel.isFavorite(data)
        isFavorite.isClickable = true

        isFavorite.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.saveFavoriteMovie(data)
            } else {
                viewModel.deleteFavoriteMovie(data)
            }
        }
    }

    private fun initGenreAdapter(genres: ArrayList<MovieGenre>?) {
        val adapter = GenreAdapter(genres ?: ArrayList())

        val layoutManager = FlexboxLayoutManager(this)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START

        rvGenres.adapter = adapter
        rvGenres.layoutManager = layoutManager
        rvGenres.isNestedScrollingEnabled = false
    }
}
