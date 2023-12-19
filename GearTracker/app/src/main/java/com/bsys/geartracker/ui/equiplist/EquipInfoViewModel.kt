package com.bsys.geartracker.ui.equiplist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsys.geartracker.data.model.dto.Equipment
import com.bsys.geartracker.data.repository.EquipInfoRepository
import kotlinx.coroutines.launch

class EquipInfoViewModel: ViewModel() {
    private val equipInfoRepository: EquipInfoRepository by lazy {EquipInfoRepository()}

    // 서버에서 받은 장비출고현황
    private val _equipList: MutableLiveData<List<Equipment>> = MutableLiveData(listOf(Equipment("3번 장비", "serial3")))
    val equipList: LiveData<List<Equipment>>
        get() = _equipList

    // 서버에서 받은 특정 장비 상세 정보
    private val _equipInfo: MutableLiveData<Equipment> = MutableLiveData()
    val equipInfo: LiveData<Equipment>
        get() = _equipInfo

    // 서버에 장비출고현황 요청
    fun get_total_equip_list(start: Int, amount: Int) {
        viewModelScope.launch {
            val result = equipInfoRepository.get_total_info_list(start, amount)
            if(result.isSuccess) { // 서버 통신 성공
                val data = result.getOrNull()
                _equipList.value = data?.equipList
                Log.d("equiplist", "data $data")
            } else { // 서버 통신 실패
                val error = result.exceptionOrNull()
                Log.d("equiplist", "error ${error}")

                // API 연결 없이 테스트 위한 자료 todo 지우기
                _equipList.value = listOf(
                    Equipment("1번 장비", "serial1"),
                    Equipment("2번 장비", "serial2")
                )
                Log.d("equiplist", "_equipList에 값 입력 ${equipList.value.toString()}")
            }
        }
    }

    // 서버에 재물조사현황 요청
    fun get_equip_inventory_list(start: Int, amount: Int) {
        viewModelScope.launch {
            val result = equipInfoRepository.get_inventory_info_list(start, amount)
            if(result.isSuccess) { // 서버 통신 성공
                val data = result.getOrNull()
                _equipList.value = data?.equipList
                Log.d("equiplist", "data $data")
            } else { // 서버 통신 실패
                val error = result.exceptionOrNull()
                Log.d("equiplist", "error ${error}")

                // API 연결 없이 테스트 위한 자료 todo 지우기
                _equipList.value = listOf(
                    Equipment("1번 장비", "serial1"),
                    Equipment("2번 장비", "serial2")
                )
                Log.d("equiplist", "_equipList에 값 입력 ${equipList.value.toString()}")
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
                _equipInfo.value = Equipment("equipname", "serialNumber")
                Log.d("equipdetail", "equipinfo에 값 입력 ${equipInfo.value.toString()}")
            }
        }
    }
}