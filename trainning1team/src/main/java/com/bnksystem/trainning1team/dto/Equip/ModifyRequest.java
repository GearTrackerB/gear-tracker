package com.bnksystem.trainning1team.dto.Equip;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModifyRequest {
    private String originSerialNo;
    private String serialNo;
    private String eqNm;
    private String eqType;
    private int typeId;
    private String eqModel;
    private String eqStatus;
    private int statusId;
    private String empNo;
    private String regAt;
    private String qrImageUrl;

}
