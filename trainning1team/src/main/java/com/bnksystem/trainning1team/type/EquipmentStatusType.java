package com.bnksystem.trainning1team.type;

import lombok.Getter;

public enum EquipmentStatusType {
    출고예정(1), 출고(2), 반납예정(3), 반납(4);

    @Getter
    private final int statusCode;

    EquipmentStatusType(int statusCode){
        this.statusCode = statusCode;
    }
}
