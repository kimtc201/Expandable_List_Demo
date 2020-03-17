package com.github.tckim.expandable_list_demo.di

import com.github.tckim.expandable_list_demo.ui.activity.MainActivity
import com.github.tckim.expandable_list_demo.di.activity.*
import com.github.tckim.expandable_list_demo.ui.activity.PartResultActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Activity Module
 */
@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun partResultActivity(): PartResultActivity
}