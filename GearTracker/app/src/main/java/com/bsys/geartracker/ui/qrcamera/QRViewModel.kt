package com.bsys.geartracker.ui.qrcamera

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsys.geartracker.data.model.dto.Equipment
import com.bsys.geartracker.data.repository.QRRequestRepository
import kotlinx.coroutines.launch

class QRViewModel: ViewModel() {

    private val qrRequestReadable: QRRequestRepository by lazy { QRRequestRepository() }

    private val _qrResult: MutableLiveData<Int> = MutableLiveData(0)
    val qrResult: LiveData<Int>
        get() = _qrResult


    // 출고 처리
    fun equip_send_request() {
        viewModelScope.launch {
            val result = qrRequestReadable.equip_send_request()
            if(result.isSuccess) { // 서버 통신 성공
                _qrResult.value = 200
            } else { // 서버 통신 실패
                val error = result.exceptionOrNull()
                Log.d("equip_send_request", "error ${error}")

                // API 연결 없이 테스트 위한 자료 todo 지우기
                _qrResult.value = 200

                // _qrResult.value = 400 todo 연결 실패 시 처리
            }
        }
    }

    // 반납 처리
    fun equip_take_request() {
        viewModelScope.launch {
            val result = qrRequestReadable.equip_take_request()
            if(result.isSuccess) { // 서버 통신 성공
                _qrResult.value = 200
            } else { // 서버 통신 실패
                val error = result.exceptionOrNull()
                Log.d("equip_take_request", "error ${error}")

                // API 연결 없이 테스트 위한 자료 todo 지우기
                _qrResult.value = 200

                // _qrResult.value = 400 todo 연결 실패 시 처리
            }
        }
    }

    // 재물 조사 처리
    fun equip_inventory_check_request() {
        viewModelScope.launch {
            val result = qrRequestReadable.equip_inventory_request()
            if(result.isSuccess) { // 서버 통신 성공
                _qrResult.value = 200
            } else { // 서버 통신 실패
                val error = result.exceptionOrNull()
                Log.d("equip_inventory_request", "error ${error}")

                // API 연결 없이 테스트 위한 자료 todo 지우기
                _qrResult.value = 200

                // _qrResult.value = 400 todo 연결 실패 시 처리
            }
        }
    }

    // 장비 상세 정보 -> equip viewmodel 쪽
    fun get_equip_detail_info() {

    }
}