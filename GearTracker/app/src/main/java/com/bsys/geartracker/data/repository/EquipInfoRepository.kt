package com.bsys.geartracker.data.repository

import com.bsys.geartracker.data.datasource.EquipInfoRemoteDatasource
import com.bsys.geartracker.data.model.dto.Equipment
import com.bsys.geartracker.data.model.response.TotalEquipResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EquipInfoRepository {
    private val equipInfoRemoteDatasource: EquipInfoRemoteDatasource by lazy {
        EquipInfoRemoteDatasource()
    }

    // 장비출납현황조회
    suspend fun get_total_info_list(lastIdx: Long, size: Int): Result<TotalEquipResponse>{
        return withContext(Dispatchers.IO) {

            equipInfoRemoteDatasource.get_total_info_list(lastIdx, size)
        }
    }

    suspend fun get_inventory_info_list(lastIdx: Int, amount: Int): Result<TotalEquipResponse>{
        return withContext(Dispatchers.IO) {

            //todo test 데이터 삭제
            Result.failure(Exception("테스트"))
            //todo API 연결
//            equipInfoRemoteDatasource.get_inventry_list(lastIdx, amount)
        }
    }

    suspend fun get_equip_detail(): Result<Equipment>{
        return withContext(Dispatchers.IO) {

            //todo API 연결
            equipInfoRemoteDatasource.get_equip_detail()
        }
    }
}