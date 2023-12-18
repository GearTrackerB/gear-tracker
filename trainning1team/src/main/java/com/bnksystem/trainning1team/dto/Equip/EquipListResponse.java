package com.bnksystem.trainning1team.dto.Equip;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class EquipListResponse {
    private String lastIdx;
    private List<EquipRentalStatusResponse> equipList;

    public EquipListResponse(String lastIdx, List<EquipRentalStatusResponse> equipList) {
        this.lastIdx = lastIdx;
        this.equipList = equipList;
    }
}
