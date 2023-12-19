package com.bsys.geartracker.data.model.response

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class RentalStatusResponse (
    @SerializedName("id") val id: Long,
    @SerializedName("serialNo") val serialNo: String,
    @SerializedName("equipmentName") val equipmentName: String,
    @SerializedName("statusName") val statusName: String,
    @SerializedName("employeeNo") val employeeNo: String,
)