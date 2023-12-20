package com.bsys.geartracker.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsys.geartracker.ApplicationClass
import com.bsys.geartracker.data.model.dto.User
import com.bsys.geartracker.data.repository.UserRepository
import kotlinx.coroutines.launch

class LogInViewModel: ViewModel() {
    private val userRepository: UserRepository by lazy { UserRepository() }

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _empNo = MutableLiveData("0")
    val empNo: LiveData<String>
        get() = _empNo

    // 서버에 로그인 요청
    fun user_log_in(user: User) {
        viewModelScope.launch {
            change_loading_state()

            val result = userRepository.log_in(user)
            Log.d("loginviewmodel", user.toString())
            if(result.isSuccess) { // 로그인 성공 시, 사원 번호 저장
                val data = result.getOrNull()
                _empNo.value = data?.empNo
            } else { // 로그인 실패
                val error = result.exceptionOrNull()
                _empNo.value = "-1" // 실패시 empNo -1
                Log.d("loginviewmodel", error.toString())
            }

            change_loading_state()
        }
    }

    // 로딩 중인지 변경
    private fun change_loading_state() {
        _isLoading.value = !_isLoading.value!!
    }

    fun test() {
        viewModelScope.launch {
            userRepository.test()
        }
    }
}