package com.github.tckim.expandable_list_demo.network.api

import com.github.tckim.expandable_list_demo.network.EndPoint
import com.github.tckim.expandable_list_demo.network.response.PartResponse
import com.github.tckim.expandable_list_demo.network.response.PartResultResponse
import io.reactivex.Single
import retrofit2.http.GET

interface ExamApi{
    /**
     * 목록 조회 API
     */
    @GET(EndPoint.GET_PART)
    fun getPart(): Single<PartResponse>

    /**
     * 부분 결과 조회 API
     */
    @GET(EndPoint.GET_PART_RESULT)
    fun getPartResult(): Single<PartResultResponse>
}