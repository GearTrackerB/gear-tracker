package com.bsys.geartracker.ui.equiplist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsys.geartracker.data.model.response.RentalStatusResponse
import com.bsys.geartracker.data.repository.EquipInfoRepository
import com.bsys.geartracker.utils.LIST_SIZE
import kotlinx.coroutines.launch

class EquipInfoViewModel: ViewModel() {
    private val equipInfoRepository: EquipInfoRepository by lazy {EquipInfoRepository()}

    // 페이징 검색 시 전달 할 마지막 장비 index
    private var lastEquipIdx: Long = 0

    // 서버에서 받은 장비출고현황
    private val _equipList: MutableLiveData<List<RentalStatusResponse>> = MutableLiveData(listOf())
    val equipList: LiveData<List<RentalStatusResponse>>
        get() = _equipList

    // 서버에서 받은 재물조사현황
    private val _inventoryList: MutableLiveData<List<RentalStatusResponse>> = MutableLiveData(listOf())
    val inventoryList: LiveData<List<RentalStatusResponse>>
        get() = _inventoryList

    // 서버에서 받은 특정 장비 상세 정보
    private val _equipInfo: MutableLiveData<com.bsys.geartracker.data.model.dto.Equipment> = MutableLiveData()
    val equipInfo: LiveData<com.bsys.geartracker.data.model.dto.Equipment>
        get() = _equipInfo

    // 서버에 장비출고현황 요청
    fun get_total_equip_list() {
        viewModelScope.launch {
            Log.d("equiplist", "장비출고현황호출")
            val result = equipInfoRepository.get_total_info_list(lastEquipIdx, LIST_SIZE)
            if(result.isSuccess) { // 서버 통신 성공 시 마지막 idx, 장비 리스트 입력
                val data = result.getOrNull()
                lastEquipIdx = data?.lastIdx ?: -1L
                _equipList.value = data?.equipList
                Log.d("equiplist", "data $data")
            } else { // 서버 통신 실패
                val error = result.exceptionOrNull()
                Log.d("equiplist", "error ${error}")
            }
        }
    }

    // 서버에 재물조사현황 요청
    fun get_equip_inventory_list(start: Int, amount: Int) {
        viewModelScope.launch {
            val result = equipInfoRepository.get_inventory_info_list(start, amount)
            if(result.isSuccess) { // 서버 통신 성공
                val data = result.getOrNull()
                lastEquipIdx = data?.lastIdx ?: -1L
                _equipList.value = data?.equipList
                Log.d("equiplist", "data $data")
            } else { // 서버 통신 실패
                val error = result.exceptionOrNull()
                Log.d("equiplist", "error ${error}")
            }
        }
    }

    // 서버에 특정 장비 정보 요청
    fun get_equip_detail() {
        viewModelScope.launch {
            val result = equipInfoRepository.get_equip_detail()
            if(result.isSuccess) { // 서버 통신 성공
                val data = result.getOrNull()
                _equipInfo.value = data!!
                Log.d("equipdetail", "equipdetail success $data")
            } else { // 서버 통신 실패
                val error = result.exceptionOrNull()
                Log.d("equipdetail", "equipdetail error ${error}")

                // API 연결 없이 테스트 위한 자료 todo 지우기
                _equipInfo.value =
                    com.bsys.geartracker.data.model.dto.Equipment("equipname", "serialNumber")
                Log.d("equipdetail", "equipinfo에 값 입력 ${equipInfo.value.toString()}")
            }
        }
    }
}