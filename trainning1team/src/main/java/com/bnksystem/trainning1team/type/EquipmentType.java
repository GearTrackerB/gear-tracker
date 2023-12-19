package com.bnksystem.trainning1team.type;

import lombok.Getter;

public enum EquipmentType {
    노트북(5), 핸드폰(6), 워치(7);

    @Getter
    private final int statusCode;

    static public String getStatusName(int statusCode){
        if(statusCode == 5){
            return "노트북";
        }
        else if(statusCode == 6){
            return "핸드폰";
        }
        else {
            return "워치";
        }
    }

    EquipmentType(int statusCode){
        this.statusCode = statusCode;
    }
}
