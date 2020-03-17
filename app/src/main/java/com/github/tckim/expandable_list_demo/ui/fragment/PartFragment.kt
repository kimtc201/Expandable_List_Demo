package com.github.tckim.expandable_list_demo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.tckim.expandable_list_demo.app.createPartResult
import com.github.tckim.expandable_list_demo.databinding.FragmentMainBinding
import com.github.tckim.expandable_list_demo.network.setDefaultObserver
import com.github.tckim.expandable_list_demo.ui.adapter.PartAdapter
import com.github.tckim.expandable_list_demo.ui.decoration.SimpleItemDecoration
import com.github.tckim.expandable_list_demo.ui.viewmodel.PartViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class PartFragment @Inject constructor() : DaggerFragment() {

    @Inject lateinit var viewModel: PartViewModel
    private lateinit var binding: FragmentMainBinding
    private val adapter = PartAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.recyclerView.adapter = adapter.apply {
            onItemClickListener = { context?.createPartResult(it.name, it.code)?.let { intent -> startActivity(intent) } }
        }
        binding.recyclerView.addItemDecoration(SimpleItemDecoration(context))

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.dataResult.setDefaultObserver(this)
        viewModel.items.observe( viewLifecycleOwner , adapter)

        viewModel.requestData()
    }

}
