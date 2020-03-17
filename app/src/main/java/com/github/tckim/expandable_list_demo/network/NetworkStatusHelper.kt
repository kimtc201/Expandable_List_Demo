package com.github.tckim.expandable_list_demo.network

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.github.tckim.expandable_list_demo.app.observer.NetworkFailedObserver
import com.github.tckim.expandable_list_demo.app.observer.ProgressObserver
import com.github.tckim.expandable_list_demo.network.response.Response
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException
import javax.net.ssl.SSLException
import javax.net.ssl.SSLHandshakeException

fun Throwable.toNetworkFailed(): NetworkStatus.Failed = NetworkStatus.Failed().also {
    it.message = if (this is HttpException) message() else this.message.orEmpty()
    it.code = when (this) {
        is HttpException -> code().toString()
        is SocketTimeoutException, is TimeoutException -> "Timeout"
        is UnknownHostException -> "UnknownHost"
        is ConnectException, is SSLException, is SSLHandshakeException -> "ConnectFail"
        else -> "Unknown Error"
    }
}

fun Response.toNetworkStatus(): NetworkStatus = (if (isSuccess()) NetworkStatus.Success() else NetworkStatus.Failed()).also {
    it.code = code
    it.message = message
}

fun MutableLiveData<NetworkStatus>.setDefaultObserver(activity: AppCompatActivity, useFailedObserver: Boolean = true, useProgressObserver: Boolean = true) {
    this.setDefaultObserver(activity, activity, useFailedObserver, useProgressObserver)
}

fun MutableLiveData<NetworkStatus>.setDefaultObserver(fragment: Fragment, useFailedObserver: Boolean = true, useProgressObserver: Boolean = true) {
    fragment.context?.let {
        this.setDefaultObserver(it, fragment, useFailedObserver, useProgressObserver)
    }
}

private fun MutableLiveData<NetworkStatus>.setDefaultObserver(context: Context?, lifecycle: LifecycleOwner, useFailedObserver: Boolean, useProgressObserver: Boolean) {
    context?.let {
        if (useFailedObserver)
            this.observe(lifecycle, NetworkFailedObserver(it))
        if (useProgressObserver)
            this.observe(lifecycle, ProgressObserver(it))
    }
}