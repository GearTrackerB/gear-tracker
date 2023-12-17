package com.bnksystem.trainning1team.mapper;

import com.bnksystem.trainning1team.dto.QR.ChangeEquipmentStatusDto;
import com.bnksystem.trainning1team.dto.QR.EquipmentStatus;
import com.bnksystem.trainning1team.dto.QR.QRRequest;
import com.bnksystem.trainning1team.dto.QR.RecordDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QRMapper {

    EquipmentStatus checkStatus(QRRequest qrRequest);

    void setEquipmentToCheckout(ChangeEquipmentStatusDto changeEquipmentStatusDto);

    void recordEquipmentCheckout(RecordDto recordDto);

    void inspectEquipment(EquipmentStatus status);

    void inspectRecord(EquipmentStatus status);
}
