package com.github.tckim.expandable_list_demo.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.github.tckim.expandable_list_demo.BuildConfig
import com.github.tckim.expandable_list_demo.network.api.ExamApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Network Dagger Module
 */
@Module
class NetworkModule {
    @Singleton
    @Provides
    fun checkApi(retrofit: Retrofit): ExamApi = retrofit.create(ExamApi::class.java)

    @Singleton
    @Provides
    fun retrofit(client: OkHttpClient, gson: Gson): Retrofit = Retrofit.Builder().apply {
        baseUrl(BuildConfig.API_URL)
        addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        addConverterFactory(GsonConverterFactory.create(gson))
        client(client)
    }.build()

    @Singleton
    @Provides
    fun okHttpClient(): OkHttpClient = OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG)
            addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
    }.build()

    @Singleton
    @Provides
    fun gson(): Gson = GsonBuilder().create()
}