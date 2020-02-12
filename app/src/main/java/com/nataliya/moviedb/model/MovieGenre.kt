package com.nataliya.moviedb.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieGenre(

    @PrimaryKey
    val id: Int,

    val name: String
)

