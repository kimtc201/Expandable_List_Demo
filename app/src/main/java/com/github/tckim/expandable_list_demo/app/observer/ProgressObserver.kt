package com.github.tckim.expandable_list_demo.app.observer

import android.content.Context
import androidx.lifecycle.Observer
import com.github.tckim.expandable_list_demo.app.dialog.ProgressDialog
import com.github.tckim.expandable_list_demo.network.NetworkStatus

class ProgressObserver(val context: Context?) : Observer<NetworkStatus> {

    private var progress: ProgressDialog? = null

    override fun onChanged(status: NetworkStatus?) {
        when (status) {
            is NetworkStatus.Progress -> {
                context?.let {
                    if (progress?.isShowing != true) progress = ProgressDialog.show(it)
                }
            }
            else -> { progress?.dismiss() }
        }
    }

}