package com.bsys.geartracker.data.datasource

import android.util.Log
import com.bsys.geartracker.ApplicationClass
import com.bsys.geartracker.data.model.dto.Equipment
import com.bsys.geartracker.data.model.response.EquipDetailResponse
import com.bsys.geartracker.data.model.response.TotalEquipResponse

// Interface 생략
class EquipInfoRemoteDatasource {

    // 장비출고현황 조회 API 호출
    suspend fun get_total_info_list(index: Long, size: Int): Result<TotalEquipResponse> {
        return try {
            val response = ApplicationClass.equipService.get_total_equip_list(index, size)
            if(response.isSuccessful) {
                val data = response.body()!!.data
                if(data != null) {
                    Result.success(data)
                } else {
                    Result.failure(Exception("equiplist data null"))
                }
            } else {
                Result.failure(Exception("equiplist network fail"))
            }
        } catch(e: Exception) {
            Result.failure(e)
        }
    }

    // 재물조사현황 조회 API 호출
    suspend fun get_inventry_list(index: Long, size: Int): Result<TotalEquipResponse> {
        return try {
            val response = ApplicationClass.equipService.get_Inventory_equip_list(index, size)
            if(response.isSuccessful) {
                val data = response.body()!!.data
                if(data != null) {
                    Result.success(data)
                } else {
                    Result.failure(Exception("data null"))
                }
            } else {
                Result.failure(Exception("network fail"))
            }
        } catch(e: Exception) {
            Result.failure(e)
        }
    }

    // 장비 정보 조회
    suspend fun get_equip_detail(serialNo: String): Result<EquipDetailResponse> {
        return try {
            val response = ApplicationClass.equipService.get_equip_detail(serialNo)
            if(response.isSuccessful) {
                val data = response.body()!!.data
                if(data != null) {
                    Result.success(data)
                } else {
                    Result.failure(Exception("data null"))
                }
            } else {
                Result.failure(Exception("network fail"))
            }
        } catch(e: Exception) {
            Result.failure(e)
        }
    }
}