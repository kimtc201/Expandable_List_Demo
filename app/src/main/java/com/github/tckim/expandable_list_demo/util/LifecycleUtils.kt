@file: JvmName("LifecycleUtils")
package com.github.tckim.expandable_list_demo.util

import com.github.tckim.expandable_list_demo.app.reactive.Disposer
import io.reactivex.disposables.Disposable

fun Disposer.disposer(disposable: Disposable) = this.disposer.add(disposable)
