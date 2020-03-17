package com.github.tckim.expandable_list_demo.ui.decoration

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.tckim.expandable_list_demo.R

class SimpleItemDecoration(context: Context?) : RecyclerView.ItemDecoration() {

    private val dividerMargin = context?.resources?.getDimensionPixelSize(R.dimen.contents_padding) ?: 0

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.set(0, 0, 0, dividerMargin)
    }
}