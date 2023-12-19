package com.bsys.geartracker.data.repository

import com.bsys.geartracker.data.datasource.QRRemoteDatasource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QRRequestRepository {
    private val qrRemoteDatasource: QRRemoteDatasource by lazy {
        QRRemoteDatasource()
    }

    suspend fun equip_send_request(): Result<Unit>{
        return withContext(Dispatchers.IO) {
            //todo API 연결
            qrRemoteDatasource.equip_send_request()
        }
    }

    suspend fun equip_take_request(): Result<Unit>{
        return withContext(Dispatchers.IO) {
            //todo API 연결
            qrRemoteDatasource.equip_take_request()
        }
    }

    suspend fun equip_inventory_request(): Result<Unit>{
        return withContext(Dispatchers.IO) {
            //todo API 연결
            qrRemoteDatasource.equip_inventory_request()
        }
    }
}