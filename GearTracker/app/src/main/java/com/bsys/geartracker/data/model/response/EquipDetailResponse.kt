package com.bsys.geartracker.data.model.response

data class EquipDetailResponse(
    val serialNO: String,
    val eqNM: String,
    val typeNM: String,
    val eqModel: String,
    val eqMaker: String,
    val detail: String,
    val manualImgUrl: String,
    val statusNM: String
)
