package com.bnksystem.trainning1team.dto.Equip;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistRequest {
    private int eqId;
    private String serialNo;
    private String eqNm;
    private String eqMaker;
    private String eqType;
    private int typeId;
    private String eqModel;
    private String eqStatus;
    private int statusId;
    private String empNo;
    private int empId;
    private String regAt;
    private String qrImageUrl;

}
