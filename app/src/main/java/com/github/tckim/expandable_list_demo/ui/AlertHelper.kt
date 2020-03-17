package com.github.tckim.expandable_list_demo.ui

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

fun Context?.alert(resId: Int, listener: ((DialogInterface, Int) -> Unit)? = null) {
    this?.let { it.alert(it.getString(resId), listener) }
}

fun Context?.alert(message: CharSequence, listener: ((DialogInterface, Int) -> Unit)? = null) {
    this?.let {
        AlertDialog.Builder(it)
            .setMessage(message)
            .setPositiveButton(android.R.string.ok, listener)
            .show()
    }
}