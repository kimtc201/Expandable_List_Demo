package com.github.tckim.expandable_list_demo.network

sealed class NetworkStatus {

    var message: String? = null
    var code: String? = null

    object Idle : NetworkStatus()

    object Progress : NetworkStatus()

    class Success : NetworkStatus()

    class Failed : NetworkStatus()

}