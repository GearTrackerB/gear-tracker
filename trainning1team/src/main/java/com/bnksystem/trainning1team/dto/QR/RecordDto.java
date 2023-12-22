package com.bnksystem.trainning1team.dto.QR;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecordDto {

    private int eqId;
    private int statusId;
    private int memberId;
    private String imageUrl;

    public RecordDto(int eqId, int statusId, int memberId) {
        this.eqId = eqId;
        this.statusId = statusId;
        this.memberId = memberId;
    }
}
