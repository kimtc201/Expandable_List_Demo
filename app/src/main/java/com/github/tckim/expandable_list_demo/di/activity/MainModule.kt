package com.github.tckim.expandable_list_demo.di.activity

import com.github.tckim.expandable_list_demo.ui.fragment.PartFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainModule {

    @ContributesAndroidInjector
    abstract fun partFragment(): PartFragment
}