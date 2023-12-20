package com.bsys.geartracker.ui.qrcamera

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsys.geartracker.data.model.dto.QRRequest
import com.bsys.geartracker.data.model.dto.User
import com.bsys.geartracker.data.repository.QRRequestRepository
import kotlinx.coroutines.launch

class QRViewModel: ViewModel() {

    private val qrRequestReadable: QRRequestRepository by lazy { QRRequestRepository() }

    private val _qrResult: MutableLiveData<Int> = MutableLiveData(0)
    val qrResult: LiveData<Int>
        get() = _qrResult


    // 출고 처리
    fun equip_send_request(serialNo: String, empNo: String) {
        viewModelScope.launch {
            val request: QRRequest = QRRequest(serialNo, empNo)
            Log.d("equip_send_request", request.toString())
            val result = qrRequestReadable.equip_send_request(request)
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
    fun equip_take_request(serialNo: String, empNo: String) {
        viewModelScope.launch {
            val result = qrRequestReadable.equip_take_request(serialNo, empNo)
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
    fun equip_inventory_check_request(serialNo: String, empNo: String) {
        viewModelScope.launch {
            val result = qrRequestReadable.equip_inventory_request(serialNo, empNo)
            if(result.isSuccess) { // 서버 통신 성공
                _qrResult.value = 200
            } else { // 서버 통신 실패
                val error = result.exceptionOrNull()
                Log.d("equip_inventory_request", "error ${error}")
                _qrResult.value = 400
            }
        }
    }

    // 장비 상세 정보 -> equip viewmodel 쪽
    fun get_equip_detail_info() {

    }
}