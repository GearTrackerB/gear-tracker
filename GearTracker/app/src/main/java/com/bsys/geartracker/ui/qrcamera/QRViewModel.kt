package com.bsys.geartracker.ui.qrcamera

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsys.geartracker.data.model.dto.QRRequest
import com.bsys.geartracker.data.model.dto.User
import com.bsys.geartracker.data.repository.QRRequestRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class QRViewModel: ViewModel() {

    private val qrRequestReadable: QRRequestRepository by lazy { QRRequestRepository() }

    private val _qrResult: MutableLiveData<Int> = MutableLiveData(0)
    val qrResult: LiveData<Int>
        get() = _qrResult
    fun resetQrResult() {
        _qrResult.value = 0
    }


    // 출고 처리
    fun equip_send_request(qrRequestBody: RequestBody, imagePart: MultipartBody.Part) {
        viewModelScope.launch {
            Log.d("equip_send_request", "qrRequestBody, imagePart")
            val result = qrRequestReadable.equip_send_request(qrRequestBody, imagePart)
            if(result.isSuccess) { // 서버 통신 성공
                _qrResult.value = 200
            } else { // 서버 통신 실패
                val error = result.exceptionOrNull()
                Log.d("equip_send_request", "error ${error}")
                 _qrResult.value = 400
            }
        }
    }

    // 반납 처리
    fun equip_take_request(qrRequestBody: RequestBody, imagePart: MultipartBody.Part) {
        viewModelScope.launch {
            val result = qrRequestReadable.equip_take_request(qrRequestBody, imagePart)
            if(result.isSuccess) { // 서버 통신 성공
                _qrResult.value = 200
            } else { // 서버 통신 실패
                val error = result.exceptionOrNull()
                Log.d("equip_take_request", "error ${error}")
                _qrResult.value = 400
            }
        }
    }

    // 재물 조사 처리
    fun equip_inventory_check_request(qrRequestBody: RequestBody, imagePart: MultipartBody.Part) {
        viewModelScope.launch {
            val result = qrRequestReadable.equip_inventory_request(qrRequestBody, imagePart)
            if(result.isSuccess) { // 서버 통신 성공
                _qrResult.value = 200
            } else { // 서버 통신 실패
                val error = result.exceptionOrNull()
                Log.d("equip_inventory_request", "error ${error}")
                _qrResult.value = 400
            }
        }
    }


}