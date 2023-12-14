package com.bsys.geartracker.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsys.geartracker.data.model.dto.User
import com.bsys.geartracker.data.repository.UserRepository
import kotlinx.coroutines.launch

class LogInViewModel: ViewModel() {
    private val userRepository: UserRepository by lazy { UserRepository() }

    private val _userSeq = MutableLiveData(0L)
    val userSeq: LiveData<Long>
        get() = _userSeq

    fun user_log_in(user: User) {
        viewModelScope.launch {
            val result = userRepository.log_in(user)
            if(result.isSuccess) {
                val data = result.getOrNull()
                _userSeq.value = data?.seq
            } else {
                val error = result.exceptionOrNull()
                _userSeq.value = 404

                Log.d("LogInViewModel", error.toString())
            }
        }
    }
}