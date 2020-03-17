package com.github.tckim.expandable_list_demo.ui.adapter

import android.animation.Animator
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.github.tckim.expandable_list_demo.R
import com.github.tckim.expandable_list_demo.databinding.ListItemArrowTextBinding
import com.github.tckim.expandable_list_demo.network.model.Part
import com.github.tckim.expandable_list_demo.network.model.PriorityType

class PartAdapter : RecyclerView.Adapter<PartAdapter.ViewHolder>(), Observer<List<Part>> {

    private var items = emptyList<Part>()
    var onItemClickListener: ((Part) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ListItemArrowTextBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.name = item.name

        holder.binding.imageViewHighlight.visibility = if( item.priorityFlag == PriorityType.SHORT || item.priorityFlag == PriorityType.LONG) VISIBLE else GONE
        holder.binding.imageViewHighlight.setImageResource( if(item.priorityFlag == PriorityType.SHORT) R.drawable.ic_dot_red else R.drawable.ic_dot_orange)

        holder.binding.root.animation = AnimationUtils.loadAnimation(holder.binding.root.context, R.anim.fade_scale_animation)
        holder.binding.root.setOnClickListener {
            holder.binding.imageView.animate().setDuration(200).translationX(20f).setListener(
                object : Animator.AnimatorListener{
                    override fun onAnimationRepeat(animation: Animator?) {}

                    override fun onAnimationEnd(animation: Animator?) {
                        onItemClickListener?.invoke(item)
                        holder.binding.imageView.translationX = 0f
                    }

                    override fun onAnimationCancel(animation: Animator?) {}

                    override fun onAnimationStart(animation: Animator?) {}
                }
            ).start()
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onChanged(items: List<Part>?) {
        this.items = items.orEmpty()
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ListItemArrowTextBinding) : RecyclerView.ViewHolder(binding.root)

}