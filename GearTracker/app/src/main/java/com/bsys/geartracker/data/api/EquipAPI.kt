package com.bsys.geartracker.data.api

import com.bsys.geartracker.data.model.dto.Equipment
import com.bsys.geartracker.data.model.response.ApiResponse
import com.bsys.geartracker.data.model.response.EquipDetailResponse
import com.bsys.geartracker.data.model.response.TotalEquipResponse
import com.bsys.geartracker.utils.LIST_SIZE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface EquipAPI {

    // 장비출고현황 리스트 요청 / index 이후 장비 부터 / size 만큼 요청
    @GET("manager/equipment")
    suspend fun get_total_equip_list(
        @Query("index") index: Long,
        @Query("size") size: Int = LIST_SIZE
    ): Response<ApiResponse<TotalEquipResponse>>

    // 재물조사현황 리스트 요청
    @GET("manager/inventory")
    suspend fun get_Inventory_equip_list(
        @Query("index") index: Long,
        @Query("size") size: Int = LIST_SIZE
    ): Response<ApiResponse<TotalEquipResponse>>

    // 장비정보조회 요청
    @GET("manager/equipment/detail")
    suspend fun get_equip_detail(@Query("serialNo") serialNo: String
    ): Response<ApiResponse<EquipDetailResponse>>
}