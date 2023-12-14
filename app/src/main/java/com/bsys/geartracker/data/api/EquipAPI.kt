package com.bsys.geartracker.data.api

import com.bsys.geartracker.data.model.response.TotalEquipResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface EquipAPI {
    @GET("get/book/url")
    suspend fun get_total_equip_list(
        @Query("start") start: Int,
        @Query("amount") amount: Int = 10
    ): Response<TotalEquipResponse>
}