package com.nataliya.moviedb.di

import android.content.Context
import androidx.room.Room
import com.nataliya.moviedb.db.AppDatabase
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule(private val context: Context) {

    @Provides
    fun provideDatabase(): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "database")
            .allowMainThreadQueries()
            .build()
    }
}