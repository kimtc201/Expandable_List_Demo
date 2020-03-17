package com.github.tckim.expandable_list_demo.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.github.tckim.expandable_list_demo.ui.activity.PartResultActivity

fun Context?.createPartResult(partName: String?, partCode: String?, isAllOpen: Boolean = false) = this?.let {
    Intent(it, PartResultActivity::class.java).apply {
        putExtras(Bundle()
            .putIsAllOpen(isAllOpen)
            .putName(partName.orEmpty())
            .putCode(partCode.orEmpty())
        )
    }
}