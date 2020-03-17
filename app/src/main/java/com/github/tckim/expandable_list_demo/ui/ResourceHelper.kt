package com.github.tckim.expandable_list_demo.ui

import android.widget.TextView
import androidx.core.content.ContextCompat

fun TextView.setTextColorRes(colorResId: Int) {
    setTextColor(ContextCompat.getColor(context, colorResId))
}