package com.github.tckim.expandable_list_demo.ui.decoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.github.tckim.expandable_list_demo.R
import com.github.tckim.expandable_list_demo.ui.adapter.PartResultAdapter
import kotlin.math.roundToInt

class ExpandableItemDecoration(context: Context?) : RecyclerView.ItemDecoration() {

    private val dividerWidth = context?.resources?.getDimensionPixelSize(R.dimen.line_width) ?: 0
    private val divider = ColorDrawable(context?.let { ContextCompat.getColor(it, R.color.pale_grey) } ?: Color.TRANSPARENT)
    private val bounds = Rect()

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.set(0, 0, 0, dividerWidth)
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            parent.getDecoratedBoundsWithMargins(child, bounds)
            val itemPosition = parent.getChildAdapterPosition(child)
            val nextItemType = if (itemPosition != (parent.adapter?.itemCount ?: 0) - 1) parent.adapter?.getItemViewType(itemPosition + 1) ?: 0 else -1
            if (nextItemType == PartResultAdapter.TYPE_ITEM || nextItemType == PartResultAdapter.TYPE_FOOTER) {
                val bottom = bounds.bottom + child.translationY.roundToInt()
                divider.setBounds(bounds.left + child.translationX.roundToInt(), bottom - dividerWidth,
                    bounds.right + child.translationX.roundToInt(), bottom)
                divider.draw(c)
            }
        }
    }
}