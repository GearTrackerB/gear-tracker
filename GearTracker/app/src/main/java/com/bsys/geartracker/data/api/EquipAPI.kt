package com.bsys.geartracker.data.api

import com.bsys.geartracker.data.model.dto.Equipment
import com.bsys.geartracker.data.model.response.InventoryEquipResponse
import com.bsys.geartracker.data.model.response.TotalEquipResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface EquipAPI {

    // 장비출고현황 리스트 요청
    @GET("get/equip/url")
    suspend fun get_total_equip_list(
        @Query("start") start: Int,
        @Query("amount") amount: Int = 10
    ): Response<TotalEquipResponse>

    // 장비출고현황 리스트 요청
    @GET("get/equip/url")
    suspend fun get_Inventory_equip_list(
        @Query("start") start: Int,
        @Query("amount") amount: Int = 10
    ): Response<InventoryEquipResponse>

    // 장비정보조회 요청
    @GET("get/equip/url")
    suspend fun get_equip_detail(
    ): Response<Equipment>
}