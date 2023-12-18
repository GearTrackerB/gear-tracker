package com.bsys.geartracker.data.api

import com.bsys.geartracker.data.model.dto.User
import com.bsys.geartracker.data.model.response.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


// 최종적으로 서버 통신하는 곳
interface UserAPI {
    @POST("/manager/login")
    suspend fun log_in(@Body user: User): Response<UserResponse>
}