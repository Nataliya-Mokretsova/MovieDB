package com.nataliya.moviedb.di

import dagger.Module
import dagger.Provides
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Named


@Module
class NetworkModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        val client = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .connectionSpecs(listOf(ConnectionSpec.COMPATIBLE_TLS))
            .build()

        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Named("api_key")
    fun provideApiKey() = "b66ffea8276ce576d60df52600822c88"

    @Provides
    @Named("language")
    fun provideLanguage() = "en"
//    fun provideLanguage() = Locale.getDefault().language
}