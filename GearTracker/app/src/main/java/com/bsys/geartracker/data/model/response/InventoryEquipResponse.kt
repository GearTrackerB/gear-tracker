package com.bsys.geartracker.data.model.response

import com.bsys.geartracker.data.model.dto.Equipment
import com.google.gson.annotations.SerializedName

data class InventoryEquipResponse(
    val start: Int,
    @SerializedName("items")
    val equipList: List<Equipment>
)
