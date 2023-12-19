package com.bnksystem.trainning1team.dto.Equip;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EquipDetailResponse {
    private String serialNO;
    private String eqNM;
    private String typeNM;
    private String eqModel;
    private String eqMaker;
    private String detail;
    private String manualImgUrl;
    private String statusNM;
}


/*
- 장비 시리얼 번호
- 장비명
- 제품 종류
- 모델명
- 제조사
- 상세정보 : 특이사항
- 메뉴얼 img
- 출고상태

 */
