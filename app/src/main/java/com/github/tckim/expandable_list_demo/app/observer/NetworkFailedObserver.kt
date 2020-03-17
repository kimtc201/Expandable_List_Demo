package com.github.tckim.expandable_list_demo.app.observer

import android.content.Context
import android.util.Log.e
import androidx.lifecycle.Observer
import com.github.tckim.expandable_list_demo.network.NetworkStatus
import com.github.tckim.expandable_list_demo.ui.alert

class NetworkFailedObserver(val context: Context?, private var messageMap: Map<String, Int>? = null) : Observer<NetworkStatus> {

    override fun onChanged(status: NetworkStatus?) {
        when (status) {
            is NetworkStatus.Failed -> {
                e("NetworkStatus.Failed", "code=${status.code} , msg=${status.message}")
                context.alert(status.message ?: "")
            }
            is NetworkStatus.Success -> {
                messageMap?.get(status.code)?.let { context.alert(it) }
            }
        }
    }

}