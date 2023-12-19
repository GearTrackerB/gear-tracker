package com.bnksystem.trainning1team.dto.Equip;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class EquipsListResponse {
    private Long lastIdx;
    private List<RentalStatusResponse> equipList;

    public EquipsListResponse(Long lastIdx, List<RentalStatusResponse> equipList) {
        this.lastIdx = lastIdx;
        this.equipList = equipList;
    }
}