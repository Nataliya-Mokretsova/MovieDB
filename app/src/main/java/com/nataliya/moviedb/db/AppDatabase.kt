package com.nataliya.moviedb.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nataliya.moviedb.model.Movie
import com.nataliya.moviedb.model.MovieGenre

@Database(
    entities = [Movie::class, MovieGenre::class],
    version = 7,
    exportSchema = false
)
@TypeConverters(MovieGenreConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}