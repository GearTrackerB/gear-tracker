package com.bsys.geartracker.data.repository

import com.bsys.geartracker.data.datasource.QRRemoteDatasource
import com.bsys.geartracker.data.model.dto.QRRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody

class QRRequestRepository {
    private val qrRemoteDatasource: QRRemoteDatasource by lazy {
        QRRemoteDatasource()
    }

    suspend fun equip_send_request(qrRequestBody: RequestBody, imagePart: MultipartBody.Part): Result<Unit>{
        return withContext(Dispatchers.IO) {
            qrRemoteDatasource.equip_send_request(qrRequestBody, imagePart)
        }
    }

    suspend fun equip_take_request(qrRequestBody: RequestBody, imagePart: MultipartBody.Part): Result<Unit>{
        return withContext(Dispatchers.IO) {
            qrRemoteDatasource.equip_take_request(qrRequestBody, imagePart)
        }
    }

    suspend fun equip_inventory_request(qrRequestBody: RequestBody, imagePart: MultipartBody.Part): Result<Unit>{
        return withContext(Dispatchers.IO) {
            qrRemoteDatasource.equip_inventory_request(qrRequestBody, imagePart)
        }
    }
}