package com.github.tckim.expandable_list_demo.app.reactive

import io.reactivex.disposables.CompositeDisposable

interface Disposer {

    val disposer: CompositeDisposable

}