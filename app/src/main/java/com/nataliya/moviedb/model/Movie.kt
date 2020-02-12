package com.nataliya.moviedb.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@Entity
data class Movie(

    @PrimaryKey
    val id: Int,

    @SerializedName("original_title")
    val originalTitle: String?,

    @SerializedName("original_language")
    val originalLanguage: String?,

    val title: String?,

    val popularity: Float,

    val overview: String?,

    @SerializedName("release_date")
    val releaseDate: String?,

    @SerializedName("poster_path")
    var posterPath: String?,

    @SerializedName("backdrop_path")
    var backDropPath: String?,

    @SerializedName("vote_average")
    var voteAverage: Float,

    var budget: Int?,

    var genres: ArrayList<MovieGenre>?
) {

    fun getPosterPath(string: String?): String? {
        return if (string != null) "https://image.tmdb.org/t/p/original$string" else null
    }

    fun getBackDropPath(string: String?): String? {
        return if (string != null) "https://image.tmdb.org/t/p/original$string" else null
    }

    fun getReleaseDate(string: String?): String? {
        val format = SimpleDateFormat(
            "yyyy-MM-dd",
//            Locale.getDefault()
            Locale.US
        )
        try {
            val date = format.parse(string!!)
            val formatS = SimpleDateFormat(
                "dd MMM yyyy",
//            Locale.getDefault()
                Locale.US
            )
            return formatS.format(date!!)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return null
    }
}

