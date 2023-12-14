package com.bsys.geartracker.ui.totalinfo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsys.geartracker.data.model.dto.Equipment
import com.bsys.geartracker.data.repository.TotalEquipInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TotalInfoViewModel: ViewModel() {
    private val totalEquipInfoRepository: TotalEquipInfoRepository by lazy {TotalEquipInfoRepository()}

    private val _equipList: MutableLiveData<List<Equipment>> = MutableLiveData(listOf(Equipment("3번 장비", "serial3")))
    val equipList: LiveData<List<Equipment>>
        get() = _equipList

    fun get_total_equip_list(start: Int, amount: Int) {
        viewModelScope.launch {
            val result = totalEquipInfoRepository.get_total_info_list(start, amount)
            if(result.isSuccess) {
                val data = result.getOrNull()
                _equipList.value = data?.equipList
                Log.d("equiplist", "data $data")
            } else {
                val error = result.exceptionOrNull()
                // 에러에 따른 처리
                Log.d("equiplist", "error ${error}")

                // API 연결 없이 테스트 위한 자료 todo 지우기
                _equipList.value = listOf(
                    Equipment("1번 장비", "serial1"),
                    Equipment("2번 장비", "serial2")
                )
                Log.d("listview", "_equipList에 값 입력 ${equipList.value.toString()}")
            }
        }
    }
}