package com.bnksystem.trainning1team.dto.Equip;

import com.bnksystem.trainning1team.type.EquipmentStatusType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentStatusResponse {
    private String status;


    public static List<EquipmentStatusResponse> toEquipmentStatusResponse() {
        EquipmentStatusType[] equipmentStatusType = EquipmentStatusType.values();
        List<EquipmentStatusResponse> list = new ArrayList<EquipmentStatusResponse>();

        for(EquipmentStatusType type : equipmentStatusType){
            list.add(new EquipmentStatusResponse(type.name()));
        }
        return list;
    }
}
