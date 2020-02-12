package com.nataliya.moviedb.network

import com.google.gson.annotations.SerializedName
import com.nataliya.moviedb.model.Movie

data class MovieListResponse(
    val results: List<Movie>,

    @SerializedName("total_pages")
    val totalPages: Int
)