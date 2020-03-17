package com.github.tckim.expandable_list_demo.network.response

import com.google.gson.annotations.SerializedName

open class Response(
    /** 결과 코드 */
    @SerializedName("resResultCode")
    var code: String? = null,
    /** 결과 메시지 */
    @SerializedName("resResultContents")
    var message: String? = null) {

    /** 성공 응답 */
    fun isSuccess() = (code == "100")
}