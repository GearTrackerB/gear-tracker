package com.bsys.geartracker.data.repository

import com.bsys.geartracker.data.datasource.EquipInfoRemoteDatasource
import com.bsys.geartracker.data.model.dto.Equipment
import com.bsys.geartracker.data.model.response.EquipDetailResponse
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

    suspend fun get_inventory_info_list(lastIdx: Long, size: Int): Result<TotalEquipResponse>{
        return withContext(Dispatchers.IO) {

            equipInfoRemoteDatasource.get_inventry_list(lastIdx, size)
        }
    }

    suspend fun get_equip_detail(serialNo: String): Result<EquipDetailResponse>{
        return withContext(Dispatchers.IO) {

            equipInfoRemoteDatasource.get_equip_detail(serialNo)
        }
    }
}