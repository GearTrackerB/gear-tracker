package com.bnksystem.trainning1team.type;

import lombok.Getter;

public enum EquipmentStatusType {
    출고예정(1), 출고(2), 반납예정(3), 반납(4);

    @Getter
    private final int statusCode;

    static public String getStatusName(int statusCode){
        if(statusCode == 1){
            return "출고예정";
        }
        else if(statusCode == 2){
            return "출고";
        }
        else if(statusCode == 3){
            return "반납예정";
        }
        else{
            return "반납";
        }
    }

    EquipmentStatusType(int statusCode){
        this.statusCode = statusCode;
    }
}
