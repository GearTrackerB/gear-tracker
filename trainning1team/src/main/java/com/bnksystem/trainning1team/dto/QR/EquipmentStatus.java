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
        return new ChangeEquipmentStatusDto(id, EquipmentStatusType.출고.getStatusCode());
    }

    public RecordDto toRecordDto() {
        return new RecordDto(id, EquipmentStatusType.출고.getStatusCode(), memberId);
    }
}
