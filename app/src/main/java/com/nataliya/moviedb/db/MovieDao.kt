package com.nataliya.moviedb.db

import androidx.room.*
import com.nataliya.moviedb.model.Movie

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie")
    fun getAll(): List<Movie>

    @Query("SELECT * FROM movie WHERE id = :id")
    fun getById(id: Int): Movie?

    @Insert
    fun insert(data: Movie)

    @Update
    fun update(data: Movie)

    @Delete
    fun delete(data: Movie)

    @Query("DELETE FROM movie")
    fun deleteAll()
}