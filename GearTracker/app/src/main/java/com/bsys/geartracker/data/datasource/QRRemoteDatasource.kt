package com.bsys.geartracker.data.datasource

import android.util.Log
import com.bsys.geartracker.ApplicationClass
import com.bsys.geartracker.data.model.dto.QRRequest
import com.bsys.geartracker.data.model.response.ApiResponse
import com.bsys.geartracker.data.model.response.TotalEquipResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

class QRRemoteDatasource {

    // 장비 출고 요청 API 호출
    suspend fun equip_send_request(qrRequestBody: RequestBody, imagePart: MultipartBody.Part): Result<Unit> {
        return try {
            val response = ApplicationClass.qrService.equip_send_request(qrRequestBody, imagePart)
            if(response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("network fail"))
            }
        } catch(e: Exception) {
            Result.failure(e)
        }
    }

    // 장비 반납 요청 API 호출
    suspend fun equip_take_request(qrRequestBody: RequestBody, imagePart: MultipartBody.Part): Result<Unit> {
        return try {
            val response = ApplicationClass.qrService.equip_take_request(qrRequestBody, imagePart)
            if(response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("network fail"))
            }
        } catch(e: Exception) {
            Result.failure(e)
        }
    }

    // 재물 조사 처리 API 호출
    suspend fun equip_inventory_request(qrRequestBody: RequestBody, imagePart: MultipartBody.Part): Result<Unit> {
        return try {
            val response = ApplicationClass.qrService.equip_invetory_request(qrRequestBody,imagePart)
            if(response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("network fail"))
            }
        } catch(e: Exception) {
            Result.failure(e)
        }
    }
}