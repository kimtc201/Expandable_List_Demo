package com.github.tckim.expandable_list_demo.ui.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.github.tckim.expandable_list_demo.app.BaseViewModel
import com.github.tckim.expandable_list_demo.network.NetworkExecutor
import com.github.tckim.expandable_list_demo.network.NetworkStatus
import com.github.tckim.expandable_list_demo.network.api.ExamApi
import com.github.tckim.expandable_list_demo.network.model.Part
import com.github.tckim.expandable_list_demo.network.response.PartResponse
import javax.inject.Inject

class PartViewModel @Inject constructor(application: Application): BaseViewModel(application) {

    @Inject lateinit var checkApi: ExamApi

    val items = MutableLiveData<List<Part>>()

    val dataResult = MutableLiveData<NetworkStatus>()

    fun requestData(){
        NetworkExecutor.Builder<PartResponse>()
            .api(checkApi.getPart())
            .asyncScheduler()
            .disposer(this)
            .onSuccess { items.postValue(it.items) }
            .onError { items.postValue(null) }
            .networkStatus(dataResult)
            .execute()
    }
}
