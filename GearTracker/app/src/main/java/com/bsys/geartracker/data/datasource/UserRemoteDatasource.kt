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
            if(response.isSuccessful) { // 통신 성공ㅎ
                val data = response.body()
                if(data != null) {
                    Result.success(data)
                } else {
                    Log.d("logindatasource", "로그인 실패")
                    Result.failure(Exception("로그인 실패"))
                }
            } else { // 통신 실패
                // Result.failure(Exception("nework fail"))
                // API 통신 연결 전 테스트 todo 지우기
                Result.success(UserResponse(1000))
            }
        } catch (e: Exception) { // 에러
            Result.failure(e)
        }
    }
}