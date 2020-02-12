package com.nataliya.moviedb.di

import com.nataliya.moviedb.repository.Repository
import dagger.Component

@Component(modules = [NetworkModule::class, DatabaseModule::class])
interface AppComponent {
    fun inject(repository: Repository)
}