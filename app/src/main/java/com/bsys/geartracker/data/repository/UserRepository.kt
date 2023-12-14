package com.bsys.geartracker.data.repository

import com.bsys.geartracker.data.datasource.UserRemoteDatasource
import com.bsys.geartracker.data.model.dto.User
import com.bsys.geartracker.data.model.response.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository {

    private val userRemoteDatasource: UserRemoteDatasource by lazy {
        UserRemoteDatasource()
    }
    suspend fun log_in(user: User): Result<UserResponse>{
        return withContext(Dispatchers.IO) {
            userRemoteDatasource.log_in(user)
        }
    }
}