package com.github.tckim.expandable_list_demo.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.tckim.expandable_list_demo.R
import com.github.tckim.expandable_list_demo.app.getCode
import com.github.tckim.expandable_list_demo.app.getName
import com.github.tckim.expandable_list_demo.app.isAllOpen
import com.github.tckim.expandable_list_demo.databinding.ActivityPartResultBinding
import com.github.tckim.expandable_list_demo.network.setDefaultObserver
import com.github.tckim.expandable_list_demo.ui.adapter.PartResultAdapter
import com.github.tckim.expandable_list_demo.ui.decoration.ExpandableItemDecoration
import com.github.tckim.expandable_list_demo.ui.fragment.dialog.PartOpinionDialogFragment
import com.github.tckim.expandable_list_demo.ui.viewmodel.PartResultViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

/**
 * Part Result Activity
 * intent?.extras.getName() 검사명
 * intent?.extras.getCode() 검사코드
 */
class PartResultActivity : DaggerAppCompatActivity() {

    @Inject lateinit var viewModel: PartResultViewModel
    private lateinit var binding: ActivityPartResultBinding
    private var adapter = PartResultAdapter().apply {
        onItemClickListener = { title, info, standard -> showOpinionDialog(title, info, standard) }
        onIsExpandedAllItemListener = { setExpandAllButtonUI(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_part_result)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.textViewTitle.text = getString(R.string.format_activity_title, intent?.extras.getName(), intent?.extras.getCode())

        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(ExpandableItemDecoration(this))
        binding.recyclerView.setItemViewCacheSize(20)

        setExpandAllButtonUI(false)

        adapter.isAllOpen = intent?.extras.isAllOpen() ?: false

        viewModel.result.setDefaultObserver(this)
        viewModel.items.observe(this, adapter)

        viewModel.getPartResult()
    }

    override fun setTitle(title: CharSequence?) {
        binding.textViewTitle.text = title
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> { onBackPressed(); true }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showOpinionDialog(title: String?, info: String?, standard: String?){
        PartOpinionDialogFragment.create(title ?: "", standard, info).show(supportFragmentManager, null)
    }

    private fun setExpandAllButtonUI( isExpandedAll: Boolean ){
        if( isExpandedAll ){
            binding.buttonExpandAll.setText(R.string.label_collapse_all)
            binding.buttonExpandAll.setOnClickListener { adapter.collapseAll() }
        } else {
            binding.buttonExpandAll.setText(R.string.label_expand_all)
            binding.buttonExpandAll.setOnClickListener { adapter.expandAll() }
        }

        binding.buttonTop.setOnClickListener{
            (binding.recyclerView.layoutManager as LinearLayoutManager).smoothScrollToPosition(binding.recyclerView, null, 0)
        }
    }
}