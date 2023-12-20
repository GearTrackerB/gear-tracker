package com.bsys.geartracker.data.api

import com.bsys.geartracker.data.model.dto.QRRequest
import com.bsys.geartracker.data.model.response.ApiResponse
import com.bsys.geartracker.data.model.response.TotalEquipResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

// todo Server API 완성되면 Dto 수정
interface QRAPI {

    // 장비 출고 처리
    @PUT("manager/equipment/checkout")
    suspend fun equip_send_request(@Body qrRequest: QRRequest): Response<ApiResponse<Unit>>

    // 장비 반납 처리
    @PUT("manager/equipment/checkin")
    suspend fun equip_take_request(@Body qrRequest: QRRequest): Response<ApiResponse<Unit>>

    // 장비 재물 조사 처리
    @POST("manager/equipment")
    suspend fun equip_invetory_request(@Body qrRequest: QRRequest): Response<ApiResponse<Unit>>


}