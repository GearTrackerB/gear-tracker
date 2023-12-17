package com.bsys.geartracker.data.datasource

import android.util.Log
import com.bsys.geartracker.ApplicationClass
import com.bsys.geartracker.data.model.response.TotalEquipResponse

class QRRemoteDatasource {

    // 장비 출고 요청 API 호출
    suspend fun equip_send_request(): Result<Unit> {
        return try {
            val response = ApplicationClass.qrService.equip_send_request()
            if(response.isSuccessful) {
                Log.d("qrrequest", "equip_send_request 성공 ${response.code()}")
                Result.success(Unit)
            } else {
                Result.failure(Exception("network fail"))
            }
        } catch(e: Exception) {
            Result.failure(e)
        }
    }

    // 장비 반납 요청 API 호출
    suspend fun equip_take_request(): Result<Unit> {
        return try {
            val response = ApplicationClass.qrService.equip_take_request()
            if(response.isSuccessful) {
                Log.d("qrrequest", "equip_take_request 성공 ${response.code()}")
                Result.success(Unit)
            } else {
                Result.failure(Exception("network fail"))
            }
        } catch(e: Exception) {
            Result.failure(e)
        }
    }

    // 재물 조사 처리 API 호출
    suspend fun equip_inventory_request(): Result<Unit> {
        return try {
            val response = ApplicationClass.qrService.equip_invetory_request()
            if(response.isSuccessful) {
                Log.d("qrrequest", "equip_invetory_request 성공 ${response.code()}")
                Result.success(Unit)
            } else {
                Result.failure(Exception("network fail"))
            }
        } catch(e: Exception) {
            Result.failure(e)
        }
    }
}