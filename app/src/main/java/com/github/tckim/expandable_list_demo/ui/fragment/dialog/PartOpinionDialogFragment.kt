package com.github.tckim.expandable_list_demo.ui.fragment.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.github.tckim.expandable_list_demo.R
import com.github.tckim.expandable_list_demo.databinding.DialogPartOpinionBinding

class PartOpinionDialogFragment : DialogFragment() {
    private lateinit var binding: DialogPartOpinionBinding

    companion object {
        private const val KEY_TITLE = "title"
        private const val KEY_STANDARD = "standard"
        private const val KEY_DESCRIPTION = "description"
        fun create(title: String?, standard: String?, description: String?) = PartOpinionDialogFragment().apply {
            arguments = Bundle().also {
                it.putString(KEY_TITLE, title)
                it.putString(KEY_STANDARD, standard)
                it.putString(KEY_DESCRIPTION, description)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogPartOpinionBinding.inflate(inflater, container, false)

        binding.buttonClose.setOnClickListener { dismiss() }

        binding.textViewTitle.text = arguments?.getString(KEY_TITLE)

        if( arguments?.getString(KEY_STANDARD) != null ) {
            binding.textViewGuide.visibility = VISIBLE
            binding.textViewGuide.text = getString(R.string.format_part_standard, arguments?.getString(KEY_STANDARD) ?: "")
        }
        binding.textViewDescription.text = arguments?.getString(KEY_DESCRIPTION)

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(context!!, R.style.AppDialogTheme)
    }
}