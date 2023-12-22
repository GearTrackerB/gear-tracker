package com.bnksystem.trainning1team.mapper;

import com.bnksystem.trainning1team.dto.Equip.InspectorRecordDto;
import com.bnksystem.trainning1team.dto.QR.ChangeEquipmentStatusDto;
import com.bnksystem.trainning1team.dto.QR.EquipmentStatus;
import com.bnksystem.trainning1team.dto.QR.QRRequest;
import com.bnksystem.trainning1team.dto.QR.RecordDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QRMapper {

    EquipmentStatus selectEquipmentStatus(String serialNo);

    void updateEquipmentStatus(ChangeEquipmentStatusDto changeEquipmentStatusDto);

    void insertEntryExitRecordQR(RecordDto recordDto);

    void updateInspectionComplete(EquipmentStatus status);

    void insertInspectRecord(InspectorRecordDto status);
}
