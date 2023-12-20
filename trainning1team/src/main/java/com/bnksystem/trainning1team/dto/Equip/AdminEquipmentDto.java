package com.bnksystem.trainning1team.dto.Equip;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AdminEquipmentDto {
    private String serialNo; //식별코드
    private int typeId; //제품 종류
    private String eqNm;//제품 명
    private String eqModel;//모델 명
    private int statusId;//상태
    private String empNo;//배정자
    private LocalDateTime regAt;//최근 재물 조사 일

    @Override
    public String toString() {
        return "AdminEquipmentDto{" +
                "serialNo='" + serialNo + '\'' +
                ", typeId=" + typeId +
                ", eqNm='" + eqNm + '\'' +
                ", eqModel='" + eqModel + '\'' +
                ", statusId=" + statusId +
                ", empNo=" + empNo +
                ", regAt=" + regAt +
                '}';
    }
}
