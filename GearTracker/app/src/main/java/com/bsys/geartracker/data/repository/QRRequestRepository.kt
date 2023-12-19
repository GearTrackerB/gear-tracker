package com.bsys.geartracker.data.repository

import com.bsys.geartracker.data.datasource.QRRemoteDatasource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QRRequestRepository {
    private val qrRemoteDatasource: QRRemoteDatasource by lazy {
        QRRemoteDatasource()
    }

    suspend fun equip_send_request(serialNo: String, empNo: String): Result<Unit>{
        return withContext(Dispatchers.IO) {
            qrRemoteDatasource.equip_send_request(serialNo, empNo)
        }
    }

    suspend fun equip_take_request(serialNo: String, empNo: String): Result<Unit>{
        return withContext(Dispatchers.IO) {
            qrRemoteDatasource.equip_take_request(serialNo, empNo)
        }
    }

    suspend fun equip_inventory_request(serialNo: String, empNo: String): Result<Unit>{
        return withContext(Dispatchers.IO) {
            qrRemoteDatasource.equip_inventory_request(serialNo, empNo)
        }
    }
}