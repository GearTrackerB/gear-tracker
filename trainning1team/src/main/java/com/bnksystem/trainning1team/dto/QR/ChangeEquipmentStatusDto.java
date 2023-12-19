package com.bnksystem.trainning1team.dto.QR;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangeEquipmentStatusDto {

    private int statusId;
    private int updId;
    private int eqId;

}
