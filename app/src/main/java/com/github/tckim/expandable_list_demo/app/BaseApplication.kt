package com.github.tckim.expandable_list_demo.app

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.multidex.MultiDex
import com.github.tckim.expandable_list_demo.util.d
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class BaseApplication : DaggerApplication() {

    private val activityCallback = object : ActivityLifecycleCallbacks {

        override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
            activity?.let { this@BaseApplication.d("onActivityCreated >> name : ${activity::class.java.simpleName}") }
        }

        override fun onActivityStarted(activity: Activity?) {

        }

        override fun onActivityResumed(activity: Activity?) {

        }

        override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {

        }

        override fun onActivityPaused(activity: Activity?) {

        }

        override fun onActivityStopped(activity: Activity?) {

        }

        override fun onActivityDestroyed(activity: Activity?) {
            activity?.let { this@BaseApplication.d("onActivityDestroyed >> name : ${activity::class.java.simpleName}") }
        }

    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        com.github.tckim.expandable_list_demo.di.DaggerApplicationComponent.builder().application(this).build()

    /**
     * @see android.app.Application.onCreate
     */
    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(activityCallback)
    }

    override fun onTerminate() {
        super.onTerminate()
        unregisterActivityLifecycleCallbacks(activityCallback)
    }

    /**
     * @see android.app.Application.attachBaseContext
     * set MultiDex
     */
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}