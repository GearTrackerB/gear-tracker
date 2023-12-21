package com.bnksystem.trainning1team.dto.QR;

import com.bnksystem.trainning1team.dto.Member.MemberInfoDto;
import com.bnksystem.trainning1team.type.EquipmentStatusType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentStatus {

    private int memberId; //대여자
    private int eqId;
    private int statusId;
    private char completeYn;
    private int inspId;

    public ChangeEquipmentStatusDto toChangeEquipmentStatusDto(MemberInfoDto admin) {
        inspId = admin.getId();

        if (statusId == EquipmentStatusType.반납예정.getStatusCode()){
            return new ChangeEquipmentStatusDto(EquipmentStatusType.반납.getStatusCode(),inspId, eqId);
        }else{
            return new ChangeEquipmentStatusDto(EquipmentStatusType.출고.getStatusCode(), inspId, eqId);
        }
    }

    public RecordDto toRecordDto() {
        if (statusId == EquipmentStatusType.반납예정.getStatusCode()){
            return new RecordDto(eqId, EquipmentStatusType.반납.getStatusCode(), memberId);
        }else{
            return new RecordDto(eqId, EquipmentStatusType.출고.getStatusCode(), memberId);
        }
    }

    @Override
    public String toString() {
        return "EquipmentStatus{" +
                "memberId=" + memberId +
                ", eqId=" + eqId +
                ", statusId=" + statusId +
                ", completeYn=" + completeYn +
                ", inspId=" + inspId +
                '}';
    }
}
