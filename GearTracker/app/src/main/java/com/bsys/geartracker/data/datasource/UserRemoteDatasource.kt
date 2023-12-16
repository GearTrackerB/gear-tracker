package com.bsys.geartracker.data.datasource

import android.util.Log
import com.bsys.geartracker.ApplicationClass
import com.bsys.geartracker.data.model.dto.User
import com.bsys.geartracker.data.model.response.TotalEquipResponse
import com.bsys.geartracker.data.model.response.UserResponse


// 서버 통신하는 API 호출하고, 반환받은 결과 처리
class UserRemoteDatasource {
    suspend fun log_in(user: User): Result<UserResponse> {
        return try {
            // API Interface를 호출해서 HTTP 통신을 가능하게 하는 Retrofit 사용
            val response = ApplicationClass.userService.log_in(user)
            if(response.isSuccessful) {
                val data = response.body()
                if(data != null) {
                    Result.success(data)
                } else {
                    Result.failure(Exception("data null"))
                }
            } else {
                Result.failure(Exception("nework fail"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}