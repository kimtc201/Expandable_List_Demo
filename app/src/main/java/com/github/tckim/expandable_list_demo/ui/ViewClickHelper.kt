@file: JvmName("ViewClickHelper")
package com.github.tckim.expandable_list_demo.ui

import android.view.View
import com.github.tckim.expandable_list_demo.util.d
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import java.util.concurrent.TimeUnit

fun View.setThrottledClickListener(callback: ((View) -> Unit)) : Disposable {
    return ClickableObservableView(this)
        .throttleFirst(600, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread()).subscribeBy(
        onNext = callback, onError = { d("onError : ${it.message}") }
    )
}

private class ClickableObservableView(private val view: View) : Observable<View>() {
    override fun subscribeActual(observer: Observer<in View>){
        val listener = Listener(view, observer)
        observer.onSubscribe(listener)
        view.setOnClickListener(listener)
    }

    class Listener(private val view: View, private val observer: Observer<in View>) : MainThreadDisposable(), View.OnClickListener{

        override fun onDispose() = view.setOnClickListener(null)

        override fun onClick(v: View) = if( !isDisposed ) observer.onNext(v) else { d("view is disposed") }
    }
}