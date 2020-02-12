package com.nataliya.moviedb.network

import com.nataliya.moviedb.model.Movie
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkService {

    companion object {
        const val VERSION_API = 3
    }

    @GET("/${VERSION_API}/movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String?,
        @Query("page") page: Int?
    ): Observable<MovieListResponse>

    @GET("/${VERSION_API}/movie/{movie_id}")
    fun getMovie(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String?
    ): Observable<Movie>


}