package com.github.tckim.expandable_list_demo.app

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.github.tckim.expandable_list_demo.app.reactive.Disposer
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel(application: Application) : AndroidViewModel(application), Disposer {

    override val disposer = CompositeDisposable()
    val context: Context
        get() = getApplication()

    override fun onCleared() {
        disposer.clear()
        super.onCleared()
    }
}