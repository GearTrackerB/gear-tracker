package com.bsys.geartracker.data.datasource

import android.app.Application
import com.bsys.geartracker.ApplicationClass
import com.bsys.geartracker.data.model.dto.User
import com.bsys.geartracker.data.model.response.UserResponse

class UserRemoteDatasource {
    suspend fun log_in(user: User): Result<UserResponse> {
        return try {
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