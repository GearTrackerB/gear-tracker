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
            // Retrofit 통해 UserAPI 호출
            val response = ApplicationClass.userService.log_in(user)
            if(response.isSuccessful) { // 통신 성공
                val data = response.body()
                if(data != null) {
                    Log.d("logindatasource", "로그인 성공 ${data}")
                    Result.success(data)
                } else {
                    Log.d("logindatasource", "로그인 실패")
                    Result.failure(Exception("로그인 실패"))
                }
            } else { // 통신 실패
                 Log.d("logindatasource", "네트워크 실패 ${response.body()} code ${response.code()} 메시지 ${response.message()}")
                 Result.failure(Exception("network fail"))
            }
        } catch (e: Exception) { // 에러
            Result.failure(e)
        }
    }

    suspend fun test() {
        try {
            // Retrofit 통해 UserAPI 호출
            val response = ApplicationClass.userService.test()
            if(response.isSuccessful) { // 통신 성공
                val data = response.body()
                if(data != null) {
                    Log.d("logindatasource", "test data $data")
                    Result.success(data)
                } else {
                    Log.d("logindatasource", "test 실패")
                    Result.failure(Exception("test 실패"))
                }
            } else { // 통신 실패
                Log.d("logindatasource", "test 네트워크 실패 ${response.body()} code ${response.code()} 메시지 ${response.message()}")
                Result.failure(Exception("network fail"))
            }
        } catch (e: Exception) { // 에러
            Result.failure(e)
        }
    }
}