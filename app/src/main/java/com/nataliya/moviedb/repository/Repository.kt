package com.nataliya.moviedb.repository

import com.nataliya.moviedb.MovieDBApplication
import com.nataliya.moviedb.db.AppDatabase
import com.nataliya.moviedb.db.MovieDao
import com.nataliya.moviedb.model.Movie
import com.nataliya.moviedb.network.MovieListResponse
import com.nataliya.moviedb.network.NetworkService
import io.reactivex.Observable
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

class Repository {

    @Inject
    lateinit var retrofit: Retrofit

    @Inject
    @field:Named("api_key")
    lateinit var apiKey: String

    @Inject
    @field:Named("language")
    lateinit var language: String

    @Inject
    lateinit var database: AppDatabase

    private val movieDao: MovieDao

    private var service: NetworkService

    init {
        MovieDBApplication.getComponent().inject(this)

        movieDao = database.movieDao()

        service = retrofit.create(NetworkService::class.java)
    }

    fun getPopularMoviesFromNetwork(page: Int): Observable<MovieListResponse> {
        return service.getPopularMovies(apiKey = apiKey, language = language, page = page)
            .map {
                it
            }
    }

    fun getMovieFromNetwork(id: Int): Observable<Movie> {
        return service.getMovie(apiKey = apiKey, language = language, id = id)
            .map {
                it
            }
    }

    fun getFavoriteMoviesCache(): List<Movie> {
        return movieDao.getAll()
    }

    fun getMovieCache(id: Int): Movie? {
        return movieDao.getById(id)
    }

    fun saveFavoriteMovie(movie: Movie) {
        if (movieDao.getById(movie.id) != null) {
            movieDao.update(movie)
        } else {
            movieDao.insert(movie)
        }
    }

    fun deleteFavoriteMovie(movie: Movie) {
        if (movieDao.getById(movie.id) != null) {
            movieDao.delete(movie)
        }
    }

    fun isFavoriteMovie(id: Int): Boolean {
        return movieDao.getById(id) != null
    }

}