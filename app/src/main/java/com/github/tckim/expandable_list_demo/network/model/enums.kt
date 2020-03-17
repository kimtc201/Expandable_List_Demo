package com.github.tckim.expandable_list_demo.network.model

import com.google.gson.annotations.SerializedName
import com.github.tckim.expandable_list_demo.R

enum class PriorityType {
    /** 단기 */
    @SerializedName("S") SHORT,
    /** 장기 */
    @SerializedName("L") LONG,
    /** 기타 */
    @SerializedName("E") ETC,
    /** 없음 */
    @SerializedName("N") NONE
}

enum class ColorFlag(val resColor: Int) {
    /** 정상 */
    @SerializedName("0") NORMAL(android.R.color.black),
    /** 낮음 */
    @SerializedName("1") LOW(R.color.blue_blue),
    /** 높음 */
    @SerializedName("2") HIGH(R.color.coral)
}