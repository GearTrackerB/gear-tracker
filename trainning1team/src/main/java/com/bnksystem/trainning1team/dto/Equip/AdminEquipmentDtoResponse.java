package com.bnksystem.trainning1team.dto.Equip;

import com.bnksystem.trainning1team.type.EquipmentStatusType;
import com.bnksystem.trainning1team.type.EquipmentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminEquipmentDtoResponse {

    private String serialNo; //식별코드
    private String eqType; //제품 종류
    private String eqNm;//제품 명
    private String eqModel;//모델 명
    private String eqStatus;//상태
    private String empNo;//배정자
    private String regAt;//최근 재물 조사 일


    public AdminEquipmentDtoResponse(AdminEquipmentDto data) {
        this.serialNo = data.getSerialNo();
        this.eqType = EquipmentType.getStatusName(data.getTypeId());
        this.eqNm = data.getEqNm();
        if(data.getEqModel() == null){
            this.eqModel = "-";
        }else{
            this.eqModel = data.getEqModel();
        }
        this.eqStatus = EquipmentStatusType.getStatusName(data.getStatusId());
        if(data.getEmpNo() == null){
            this.empNo = "-";
        }else{
            this.empNo = data.getEmpNo();
        }
        if(data.getRegAt() == null){
            this.regAt = "-";
        }else{
            this.regAt = data.getRegAt().format(DateTimeFormatter.ISO_DATE);
        }
    }
}
