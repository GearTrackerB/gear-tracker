package com.bsys.geartracker.data.repository

import com.bsys.geartracker.data.datasource.TotalInfoRemoteDatasource
import com.bsys.geartracker.data.model.dto.Equipment
import com.bsys.geartracker.data.model.dto.User
import com.bsys.geartracker.data.model.response.TotalEquipResponse
import com.bsys.geartracker.data.model.response.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TotalEquipInfoRepository {
    private val totalInfoRemoteDatasource: TotalInfoRemoteDatasource by lazy {
        TotalInfoRemoteDatasource()
    }
    suspend fun get_total_info_list(start: Int, amount: Int): Result<TotalEquipResponse>{
        return withContext(Dispatchers.IO) {
            Result.failure(Exception("테스트"))
            //todo API 연결
//            totalInfoRemoteDatasource.get_total_info_list(start, amount)
        }
    }
}