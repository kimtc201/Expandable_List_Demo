package com.github.tckim.expandable_list_demo.network.model

import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.github.tckim.expandable_list_demo.network.DateDashJsonAdapter

/**
 * 검사
 * @see com.github.tckim.expandable_list_demo.network.api.ExamApi.getPart
 */
data class Part(
    /** 검사코드 */
    @SerializedName("code") val code: String?,
    /** 검사명 */
    @SerializedName("title") val name: String?,
    /** 우선순위 */
    @SerializedName("priority") val priorityFlag: PriorityType?
)

/**
 * 검사 결과
 * @see com.github.tckim.expandable_list_demo.network.api.ExamApi.getPartResult
 */
data class PartResult(
    /** 코드 */
    @SerializedName("code") val partCode: String?,
    /** 데이터 구조 flag(1: 텍스트, 2: 테이블) */
    @SerializedName("flag") val flag: String?,
    /** 소제목 */
    @SerializedName("subTitle") val subPartName: String?,
    /** 우선순위 */
    @SerializedName("priority") val priorityFlag: PriorityType?,
    /** 설명 */
    @SerializedName("description") val description: String?,
    /** 부분 결과 조회 상세 헤더 */
    @SerializedName("subDateList") val headers: List<PartResultHeader>?,
    /** 부분 결과 조회 상세 목록 */
    @SerializedName("subResultList") val items: List<PartResultDetail>?
)

/**
 * 검사 결과 조회 상세
 * @see com.github.tckim.expandable_list_demo.network.api.ExamApi.getPartResult
 */
data class PartResultDetail(
    /** 검사 코드 */
    @SerializedName("code") val examCode: String?,
    /** 검사명 */
    @SerializedName("name") val examName: String?,
    /** 검사 소견 */
    @SerializedName("opinion") val opinion: String?,
    /** 기준 정보 */
    @SerializedName("range") val maleRange: String?,
    /** 결과값 1 */
    @SerializedName("result1") val result1: String?,
    /** 결과값 2 */
    @SerializedName("result2") val result2: String?,
    /** 결과값 3 */
    @SerializedName("result3") val result3: String?,
    /** 결과값 1 컬러값 */
    @SerializedName("colorFlag1") val colorFlag1: ColorFlag?,
    /** 결과값 2 컬러값 */
    @SerializedName("colorFlag2") val colorFlag2: ColorFlag?,
    /** 결과값 3 컬러값 */
    @SerializedName("colorFlag3") val colorFlag3: ColorFlag?
)

/**
 * 검사 결과 조회 상세 헤더
 * @see com.github.tckim.expandable_list_demo.network.api.ExamApi.getPartResult
 */
data class PartResultHeader(
    /** 번호 */
    @SerializedName("no") val number: String?,
    /** 검사일자 */
    @SerializedName("ordDt") @JsonAdapter(DateDashJsonAdapter::class) val orderDate: Long?
)