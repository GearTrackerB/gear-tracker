package com.bnksystem.trainning1team.dto.QR;

import com.bnksystem.trainning1team.type.EquipmentStatusType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentStatus {

    private int memberId;
    private int id; //장비id
    private int statusId;

    public ChangeEquipmentStatusDto toChangeEquipmentStatusDto() {
        if (statusId == EquipmentStatusType.반납예정.getStatusCode()){
            return new ChangeEquipmentStatusDto(id, EquipmentStatusType.반납.getStatusCode());
        }else{
            return new ChangeEquipmentStatusDto(id, EquipmentStatusType.출고.getStatusCode());
        }
    }

    public RecordDto toRecordDto() {
        if (statusId == EquipmentStatusType.반납예정.getStatusCode()){
            return new RecordDto(id, EquipmentStatusType.반납.getStatusCode(), memberId);
        }else{
            return new RecordDto(id, EquipmentStatusType.출고.getStatusCode(), memberId);
        }
    }
}
