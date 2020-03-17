package com.github.tckim.expandable_list_demo.network.response

import com.google.gson.annotations.SerializedName
import com.github.tckim.expandable_list_demo.network.model.Part
import com.github.tckim.expandable_list_demo.network.model.PartResult

/**
 * 검사 목록 조회 Response
 */
data class PartResponse(
    /** 검사 조회 목록 */
    @SerializedName("PartList") val items: List<Part>?
) : Response()

/**
 * 부분 결과 조회 Response
 */
data class PartResultResponse(
    /** 부분 결과 조회 목록 */
    @SerializedName("partResultList") val items: List<PartResult>?
) : Response()