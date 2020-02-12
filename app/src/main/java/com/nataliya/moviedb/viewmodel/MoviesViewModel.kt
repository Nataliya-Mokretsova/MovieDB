package com.nataliya.moviedb.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nataliya.moviedb.model.Movie
import com.nataliya.moviedb.repository.Repository
import com.nataliya.moviedb.state.State
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MoviesViewModel : ViewModel() {

    var page = 1
    var totalPage = 1

    private val repository = Repository()

    val state: MutableLiveData<State> = MutableLiveData()
    val movies: MutableLiveData<List<Movie>> = MutableLiveData()
    val movie: MutableLiveData<Movie> = MutableLiveData()

    init {
        state.postValue(State.Loading())
    }

    fun refresh() {
        state.postValue(State.Loading())
        page = 1
        getPopularMovies()
    }

    @SuppressLint("CheckResult")
    fun getPopularMovies() {
        if (page <= totalPage) {
            repository.getPopularMoviesFromNetwork(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    totalPage = response.totalPages

                    movies.postValue(response.results)
                    state.postValue(State.Loaded())
                }, {
                    state.postValue(State.Error())
                })

            page++
        }
    }

    @SuppressLint("CheckResult")
    fun getMovie(id: Int) {
        repository.getMovieFromNetwork(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                movie.postValue(response)
                state.postValue(State.Loaded())
            }, {
                state.postValue(State.Error())
            })

    }

    fun saveFavoriteMovie(movie: Movie) {
        repository.saveFavoriteMovie(movie)
    }

    fun deleteFavoriteMovie(movie: Movie) {
        repository.deleteFavoriteMovie(movie)
    }

    fun isFavorite(movie: Movie): Boolean {
        return repository.isFavoriteMovie(movie.id)
    }

    fun getFavoriteMovies(): List<Movie> {
        return repository.getFavoriteMoviesCache()
    }

    fun getMovieFromCache(id: Int): Movie? {
        return repository.getMovieCache(id)
    }

}