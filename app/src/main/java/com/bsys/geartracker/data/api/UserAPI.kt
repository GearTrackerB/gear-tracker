package com.bsys.geartracker.data.api

import com.bsys.geartracker.data.model.dto.User
import com.bsys.geartracker.data.model.response.UserResponse
import retrofit2.Response
import retrofit2.http.GET

interface UserAPI {

    @GET("/user/login")
    suspend fun log_in(user: User): Response<UserResponse>
}