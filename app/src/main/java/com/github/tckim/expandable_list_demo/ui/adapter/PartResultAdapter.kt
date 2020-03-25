package com.github.tckim.expandable_list_demo.ui.adapter

import android.animation.Animator
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*
import android.graphics.Paint.UNDERLINE_TEXT_FLAG
import android.text.SpannableStringBuilder
import com.github.tckim.expandable_list_demo.R
import com.github.tckim.expandable_list_demo.databinding.*
import com.github.tckim.expandable_list_demo.network.model.PartResult
import com.github.tckim.expandable_list_demo.network.model.PriorityType
import com.github.tckim.expandable_list_demo.ui.setTextColorRes
import com.github.tckim.expandable_list_demo.ui.setThrottledClickListener

class PartResultAdapter : RecyclerView.Adapter<PartResultAdapter.ViewHolder>(), Observer<List<PartResult>> {

    companion object {
        const val TYPE_ITEM = 0
        const val TYPE_FOOTER = 1
        const val TYPE_EXPANDED_TEXT = 2
        const val TYPE_EXPANDED_TABLE = 3
    }

    private var items = mutableListOf<PartResultWrapper>()
    private var expandableItems = mutableListOf<PartResultWrapper>()
    var isAllOpen = false

    var onItemClickListener: ((String?, String?, String?) -> Unit)? = null
    var onIsExpandedAllItemListener: ((Boolean) -> Unit)? = null

    private val isExpandedAllItem: Boolean
        get() = expandableItems.all { it.isExpanded }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        when (viewType) {
            TYPE_ITEM -> ListItemPartResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            TYPE_EXPANDED_TEXT -> ListItemPartResultTextBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            TYPE_FOOTER -> ViewFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            else -> ListItemPartResultTableBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        }
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if( holder.binding !is ViewFooterBinding ){
            val item = items[position]
            when (holder.binding) {
                is ListItemPartResultBinding -> {
                    holder.binding.value = item.value
                    holder.binding.root.tag = item
                    holder.binding.imageViewArrow.setImageResource(if (expandableItems[item.expandableItemIndex].isExpanded) R.drawable.ic_arrow_top else R.drawable.ic_arrow_bottom)

                    holder.binding.imageViewHighlight.visibility =
                        if( item.value?.priorityFlag == PriorityType.SHORT || item.value?.priorityFlag == PriorityType.LONG) VISIBLE else GONE

                    if (item.value?.priorityFlag == PriorityType.SHORT)
                        holder.binding.imageViewHighlight.setImageResource(R.drawable.ic_dot_red)
                    else
                        holder.binding.imageViewHighlight.setImageResource(R.drawable.ic_dot_orange)

                    holder.binding.root.setThrottledClickListener {
                        val expandItem = expandableItems[item.expandableItemIndex]
                        val expandPosition = items.indexOf(it.tag) + 1
                        if (expandItem.isExpanded) {
                            items.remove(expandItem)
                            notifyItemRemoved(expandPosition)
                            expandItem.isExpanded = false
                            onIsExpandedAllItemListener?.invoke(isExpandedAllItem)
                            holder.binding.imageViewArrow.animate().rotationBy(180f).setDuration(700).setListener(
                                object : Animator.AnimatorListener{
                                    override fun onAnimationRepeat(animation: Animator?) {}

                                    override fun onAnimationEnd(animation: Animator?) {
                                        holder.binding.imageViewArrow.setImageResource(R.drawable.ic_arrow_bottom)
                                        holder.binding.imageViewArrow.rotation = 0f
                                    }

                                    override fun onAnimationCancel(animation: Animator?) {}

                                    override fun onAnimationStart(animation: Animator?) {}
                                }
                            ).start()
                        } else {
                            items.add(expandPosition, expandItem)
                            notifyItemInserted(expandPosition)
                            expandItem.isExpanded = true
                            onIsExpandedAllItemListener?.invoke(isExpandedAllItem)
                            holder.binding.imageViewArrow.animate().rotationBy(180f).setDuration(700).setListener(
                                object : Animator.AnimatorListener{
                                    override fun onAnimationRepeat(animation: Animator?) {}

                                    override fun onAnimationEnd(animation: Animator?) {
                                        holder.binding.imageViewArrow.setImageResource(R.drawable.ic_arrow_top)
                                        holder.binding.imageViewArrow.rotation = 0f
                                    }

                                    override fun onAnimationCancel(animation: Animator?) {}

                                    override fun onAnimationStart(animation: Animator?) {}
                                }
                            ).start()
                        }
                    }
                }
                is ListItemPartResultTextBinding -> {
                    holder.binding.layoutValue2.visibility = GONE
                    holder.binding.layoutValue3.visibility = GONE
                    val context = holder.binding.root.context
                    item.value?.let {
                        holder.binding.textViewDoctorOpinion.text = context.getString(R.string.label_examination_report)
                        holder.binding.textViewDoctorOpinion.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ico_report_small, 0, 0, 0)
                        holder.binding.textViewDescription.text = it.items?.get(0)?.opinion ?: ""
                        holder.binding.textViewDescription.visibility = VISIBLE

                        if (!(it.description.isNullOrBlank())) {
                            holder.binding.buttonOpinion.visibility = VISIBLE
                            holder.binding.buttonOpinion.setThrottledClickListener { _ ->
                                onItemClickListener?.invoke(
                                    it.subPartName,
                                    it.description,
                                    null
                                )
                            }
                        } else
                            holder.binding.buttonOpinion.visibility = GONE

                        it.headers.orEmpty().forEachIndexed { index, header ->
                            when (index) {
                                0 -> {
                                    holder.binding.textViewCheckupDate.text = SimpleDateFormat(
                                        context.getString(R.string.format_report_date),
                                        Locale.getDefault()
                                    ).format(Date(header.orderDate ?: 0))
                                    it.items?.let { value ->
                                        if (value.isNotEmpty()) holder.binding.textViewContents.text = value[0].result1
                                    }
                                }
                                1 -> {
                                    holder.binding.layoutValue2.visibility = VISIBLE
                                    holder.binding.textViewCheckupDate2.text = SimpleDateFormat(
                                        context.getString(R.string.format_report_date),
                                        Locale.getDefault()
                                    ).format(Date(header.orderDate ?: 0))
                                    it.items?.let { value ->
                                        if (value.isNotEmpty()) holder.binding.textViewContents2.text = value[0].result2
                                    }
                                }
                                2 -> {
                                    holder.binding.layoutValue3.visibility = VISIBLE
                                    holder.binding.textViewCheckupDate3.text = SimpleDateFormat(
                                        context.getString(R.string.format_report_date),
                                        Locale.getDefault()
                                    ).format(Date(header.orderDate ?: 0))
                                    it.items?.let { value ->
                                        if (value.isNotEmpty()) holder.binding.textViewContents3.text = value[0].result3
                                    }
                                }
                            }
                        }
                        holder.binding.buttonOpinion.visibility = GONE
                        it.items?.let { value ->
                            if (value.isNotEmpty()) {
                                if (!(it.description.isNullOrBlank() && value[0].maleRange.isNullOrBlank())) {
                                    holder.binding.buttonOpinion.visibility = VISIBLE
                                    holder.binding.buttonOpinion.setThrottledClickListener { _ ->
                                        onItemClickListener?.invoke(
                                            it.subPartName,
                                            it.description,
                                            value[0].maleRange
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                is ListItemPartResultTableBinding -> {
                    val context = holder.binding.root.context

                    item.value?.let {
                        var opinionString = SpannableStringBuilder()
                        val opinionList = it.items.orEmpty().filter { filter -> !filter.opinion.isNullOrBlank() }
                        opinionList.forEachIndexed { index, item ->
                            if (!item.opinion.isNullOrBlank()) {
                                opinionString = opinionString.append(item.opinion)

                                if (opinionList.size - 1 != index) {
                                    opinionString = opinionString.append("\n\n")
                                }
                            }
                        }

                        holder.binding.textViewDoctorOpinion.text = context.getString(R.string.label_examination_report)
                        holder.binding.textViewDoctorOpinion.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ico_report_small, 0, 0, 0)
                        holder.binding.textViewDescription.text = opinionString
                        holder.binding.textViewDescription.visibility = VISIBLE

                        if (!(it.description.isNullOrBlank())) {
                            holder.binding.buttonOpinion.visibility = VISIBLE
                            holder.binding.buttonOpinion.setThrottledClickListener { _ ->
                                onItemClickListener?.invoke(
                                    it.subPartName,
                                    it.description,
                                    null
                                )
                            }
                        } else
                            holder.binding.buttonOpinion.visibility = GONE


                        holder.binding.viewCount = it.headers?.size ?: 0
                        it.headers.orEmpty().forEachIndexed { index, header ->
                            when (index) {
                                0 -> {
                                    holder.binding.textViewData1Label.text = SimpleDateFormat(
                                        context.getString(R.string.format_report_date),
                                        Locale.getDefault()
                                    ).format(Date(header.orderDate ?: 0))
                                }
                                1 -> {
                                    holder.binding.textViewData2Label.text = SimpleDateFormat(
                                        context.getString(R.string.format_report_date),
                                        Locale.getDefault()
                                    ).format(Date(header.orderDate ?: 0))
                                }
                                2 -> {
                                    holder.binding.textViewData3Label.text = SimpleDateFormat(
                                        context.getString(R.string.format_report_date),
                                        Locale.getDefault()
                                    ).format(Date(header.orderDate ?: 0))
                                }
                            }
                        }

                        holder.binding.layoutTest.removeAllViews()
                        it.items.orEmpty().forEach { item ->
                            ViewPartTableRowBinding.inflate(LayoutInflater.from(context), holder.binding.layoutTest, true
                            ).apply {
                                part = item
                                viewCount = it.headers?.size ?: 0
                                item.colorFlag1?.let { id -> textViewData1.setTextColorRes(id.resColor) }
                                item.colorFlag2?.let { id -> textViewData2.setTextColorRes(id.resColor) }
                                item.colorFlag3?.let { id -> textViewData3.setTextColorRes(id.resColor) }

                                if (!item.maleRange.isNullOrBlank()) {
                                    textViewExaminationLabel.paintFlags =
                                        textViewExaminationLabel.paintFlags or UNDERLINE_TEXT_FLAG
                                    textViewExaminationLabel.setThrottledClickListener {
                                        onItemClickListener?.invoke(
                                            item.examName,
                                            "",
                                            item.maleRange
                                        )
                                    }
                                }
                            }

                            ViewVerticalDividerBinding.inflate(LayoutInflater.from(context), holder.binding.layoutTest, true)
                        }
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = items.size + 1

    override fun getItemViewType(position: Int): Int = if( position == items.size ) TYPE_FOOTER else items[position].type

    override fun onChanged(newItems: List<PartResult>?) {
        this.expandableItems.clear()
        this.items = mutableListOf<PartResultWrapper>().apply {
            newItems.orEmpty().forEach { partResult ->
                expandableItems.add(PartResultWrapper(if (partResult.flag?.toInt() ?: 1 == 1) TYPE_EXPANDED_TEXT else TYPE_EXPANDED_TABLE).also { itemWrapper ->
                    itemWrapper.value = partResult
                    itemWrapper.expandableItemIndex = items.size
                })
                add(PartResultWrapper(TYPE_ITEM).also { wrapper ->
                    wrapper.value = partResult
                    wrapper.expandableItemIndex = expandableItems.size - 1
                })
            }
        }

        notifyDataSetChanged()

        if( isAllOpen || items.size == 1 )
            expandAll()
    }

    fun expandAll() {
        items.filter { !expandableItems[it.expandableItemIndex].isExpanded }.forEach { wrapper ->
            items.indexOfFirst { it == wrapper }.takeIf { it != -1 }?.apply {
                items.add(plus(1), expandableItems[wrapper.expandableItemIndex])
                notifyItemInserted(plus(1))
                expandableItems[wrapper.expandableItemIndex].isExpanded = true
                notifyItemChanged(this)
            }
        }
        onIsExpandedAllItemListener?.invoke(isExpandedAllItem)
    }

    fun collapseAll() {
        items.filter { expandableItems[it.expandableItemIndex].isExpanded }.forEach { wrapper ->
            items.indexOfFirst { it == wrapper }.takeIf { it != -1 }?.apply {
                items.remove(expandableItems[wrapper.expandableItemIndex])
                notifyItemRemoved(plus(1))
                expandableItems[wrapper.expandableItemIndex].isExpanded = false
                notifyItemChanged(this)
            }
        }
        onIsExpandedAllItemListener?.invoke(isExpandedAllItem)
    }

    class ViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)

    class PartResultWrapper(val type: Int) {
        var value: PartResult? = null
        var isExpanded: Boolean = false
        var expandableItemIndex: Int = 0
    }
}