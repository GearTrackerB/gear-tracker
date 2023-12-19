package com.bsys.geartracker.data.model.response

import com.bsys.geartracker.data.model.dto.Equipment
import com.google.gson.annotations.SerializedName

data class TotalEquipResponse(
    @SerializedName("lastIdx") val lastIdx: Long,
    @SerializedName("equipList") val equipList: List<RentalStatusResponse>
)