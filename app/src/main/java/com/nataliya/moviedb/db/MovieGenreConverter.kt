package com.nataliya.moviedb.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nataliya.moviedb.model.MovieGenre
import java.util.*

@TypeConverters
class MovieGenreConverter {

    @TypeConverter
    fun fromString(data: String?): ArrayList<MovieGenre>? {
        if (data == null) return null
        val listType = object : TypeToken<ArrayList<MovieGenre>>() {}.type
        val gson = Gson()
        return gson.fromJson<ArrayList<MovieGenre>>(data, listType)
    }

    @TypeConverter
    fun toString(data: ArrayList<MovieGenre>?): String? {
        if (data == null) return null
        val gson = Gson()
        return gson.toJson(data)
    }
}