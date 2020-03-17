package com.github.tckim.expandable_list_demo.app.dialog

import android.app.Dialog
import android.content.Context
import com.github.tckim.expandable_list_demo.R

class ProgressDialog(context: Context) : Dialog(context, R.style.AppDialogTheme_Progress) {

    init {
        setContentView(R.layout.dialog_progress)
    }

    companion object {
        fun show(context: Context) = ProgressDialog(context).apply {
            show()
        }
    }
}