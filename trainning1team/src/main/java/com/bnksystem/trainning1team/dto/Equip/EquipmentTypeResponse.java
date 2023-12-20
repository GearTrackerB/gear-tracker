package com.bnksystem.trainning1team.dto.Equip;

import com.bnksystem.trainning1team.type.EquipmentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentTypeResponse {
    private String type;

    public static List<EquipmentTypeResponse> toEquipmentTypeResponse() {
        EquipmentType[] equipmentType = EquipmentType.values();
        List<EquipmentTypeResponse> list = new ArrayList<EquipmentTypeResponse>();

        for(EquipmentType type : equipmentType){
            list.add(new EquipmentTypeResponse(type.name()));
        }
        return list;
    }
}
