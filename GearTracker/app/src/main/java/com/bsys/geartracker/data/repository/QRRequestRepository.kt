package com.bsys.geartracker.data.repository

import com.bsys.geartracker.data.datasource.QRRemoteDatasource
import com.bsys.geartracker.data.model.dto.QRRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QRRequestRepository {
    private val qrRemoteDatasource: QRRemoteDatasource by lazy {
        QRRemoteDatasource()
    }

    suspend fun equip_send_request(qrRequest: QRRequest): Result<Unit>{
        return withContext(Dispatchers.IO) {
            qrRemoteDatasource.equip_send_request(qrRequest)
        }
    }

    suspend fun equip_take_request(qrRequest: QRRequest): Result<Unit>{
        return withContext(Dispatchers.IO) {
            qrRemoteDatasource.equip_take_request(qrRequest)
        }
    }

    suspend fun equip_inventory_request(qrRequest: QRRequest): Result<Unit>{
        return withContext(Dispatchers.IO) {
            qrRemoteDatasource.equip_inventory_request(qrRequest)
        }
    }
}