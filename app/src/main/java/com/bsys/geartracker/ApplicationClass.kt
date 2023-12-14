package com.bsys.geartracker

import android.app.Application
import com.bsys.geartracker.data.api.EquipAPI
import com.bsys.geartracker.data.api.UserAPI
import com.bsys.geartracker.utils.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApplicationClass: Application(){

    companion object {
        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val userService: UserAPI by lazy {
            retrofit.create(UserAPI::class.java)
        }

        val equipService: EquipAPI by lazy {
            retrofit.create(EquipAPI::class.java)
        }
    }
    override fun onCreate() {
        super.onCreate()

    }
}