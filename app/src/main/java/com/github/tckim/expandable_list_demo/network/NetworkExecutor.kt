package com.github.tckim.expandable_list_demo.network

import androidx.lifecycle.MutableLiveData
import com.github.tckim.expandable_list_demo.app.reactive.Disposer
import com.github.tckim.expandable_list_demo.network.response.Response
import com.github.tckim.expandable_list_demo.util.disposer
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class NetworkExecutor<T : Response> {

    var api: Single<T>? = null
    var onSuccess: ((T) -> Unit)? = null
    var onError: ((Throwable) -> Unit)? = null
    var disposer: Disposer? = null
    var networkStatus: MutableLiveData<NetworkStatus>? = null

    fun execute() {
        api?.let { api ->
            networkStatus?.postValue(NetworkStatus.Progress)
            api.subscribeBy(
                onSuccess = {
                    if (it.isSuccess()) onSuccess?.invoke(it)
                    else onError?.invoke(RuntimeException(it.message))
                    networkStatus?.postValue(it.toNetworkStatus())
                },
                onError = {
                    onError?.invoke(it)
                    it.toNetworkFailed().apply {
                        networkStatus?.postValue(this)
                        it.printStackTrace()
                    }
                }
            ).apply { disposer?.disposer(this) }
        }
    }

    class Builder<T : Response> {

        private val executor = NetworkExecutor<T>()

        fun api(api: Single<T>) = apply { executor.api = api }

        fun disposer(disposer: Disposer) = apply { executor.disposer = disposer }

        fun asyncScheduler() = apply { executor.api = executor.api?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread()) }

        fun onSuccess(onSuccess: ((T) -> Unit)) = apply { executor.onSuccess = onSuccess }

        fun onError(onError: ((Throwable) -> Unit)) = apply { executor.onError = onError }

        fun networkStatus(networkStatus: MutableLiveData<NetworkStatus>) = apply { executor.networkStatus = networkStatus }

        fun execute() = executor.execute()
    }
}