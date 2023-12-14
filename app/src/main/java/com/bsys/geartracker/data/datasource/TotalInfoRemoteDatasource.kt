package com.bsys.geartracker.data.datasource

import android.util.Log
import com.bsys.geartracker.ApplicationClass
import com.bsys.geartracker.data.model.response.TotalEquipResponse

class TotalInfoRemoteDatasource {
    suspend fun get_total_info_list(start: Int, amount: Int): Result<TotalEquipResponse> {
        return try {
            val response = ApplicationClass.equipService.get_total_equip_list(start, amount)
            if(response.isSuccessful) {
                val data = response.body()
                if(data != null) {
                    Log.d("listview", "total list 가져오기 성공 ${response.code()} ${response.headers()}")
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