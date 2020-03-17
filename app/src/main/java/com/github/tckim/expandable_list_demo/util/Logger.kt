package com.github.tckim.expandable_list_demo.util

import android.util.Log
import com.github.tckim.expandable_list_demo.BuildConfig

fun Any.d(message: String) {
    println(Log.DEBUG, this.loggerTag(), message)
}

private fun println(priority: Int, tag: String?, message: String) {
    if (BuildConfig.DEBUG) Log.println(priority, tag, message)
}

private fun Any.loggerTag(): String? {
    return if (this is String) {
        this.toString()
    } else {
        this::class.simpleName?.let { if (it.length > 23) it.substring(0, 23) else it }
    }
}