package com.github.tckim.expandable_list_demo.ui.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.github.tckim.expandable_list_demo.app.BaseViewModel
import com.github.tckim.expandable_list_demo.network.NetworkExecutor
import com.github.tckim.expandable_list_demo.network.NetworkStatus
import com.github.tckim.expandable_list_demo.network.api.ExamApi
import com.github.tckim.expandable_list_demo.network.model.PartResult
import com.github.tckim.expandable_list_demo.network.response.PartResultResponse
import javax.inject.Inject

class PartResultViewModel @Inject constructor(application: Application) : BaseViewModel(application) {

    @Inject lateinit var api: ExamApi

    val result = MutableLiveData<NetworkStatus>()
    val items = MutableLiveData<List<PartResult>>()

    fun getPartResult() {
        NetworkExecutor.Builder<PartResultResponse>()
            .api(api.getPartResult())
            .asyncScheduler()
            .disposer(this)
            .onSuccess { items.postValue(it.items) }
            .onError { items.postValue(null) }
            .networkStatus(result)
            .execute()
    }

}