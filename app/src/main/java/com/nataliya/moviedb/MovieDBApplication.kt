package com.nataliya.moviedb

import android.app.Application
import com.nataliya.moviedb.di.AppComponent
import com.nataliya.moviedb.di.DaggerAppComponent
import com.nataliya.moviedb.di.DatabaseModule
import com.nataliya.moviedb.di.NetworkModule

class MovieDBApplication: Application() {

    companion object {
        private lateinit var component: AppComponent

        fun getComponent() = component
    }

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent.builder()
            .networkModule(NetworkModule())
            .databaseModule(DatabaseModule(applicationContext))
            .build()
    }
}