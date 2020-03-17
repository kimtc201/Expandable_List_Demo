package com.github.tckim.expandable_list_demo.di

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.github.tckim.expandable_list_demo.app.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ApplicationModule {

    @Binds
    abstract fun bindContext(application: Application): Context

    @Binds
    abstract fun viewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}