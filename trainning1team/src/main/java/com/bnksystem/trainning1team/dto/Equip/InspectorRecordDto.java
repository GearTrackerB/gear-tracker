package com.bnksystem.trainning1team.dto.Equip;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InspectorRecordDto {
    private int eqId;
    private int inspectorId;
    private String inspectedAt;
    private String imageUrl;

    public InspectorRecordDto(int eqId, int inspectorId, String url){
        this.eqId = eqId;
        this.inspectorId = inspectorId;
        StringBuilder sb = new StringBuilder();
        sb.append(LocalDateTime.now().getYear());
        sb.append(LocalDateTime.now().getMonthValue());
        this.inspectedAt = sb.toString();
        this.imageUrl = url;
    }
}
