package com.bnksystem.trainning1team.service;

import com.bnksystem.trainning1team.dto.Equip.*;
import com.bnksystem.trainning1team.mapper.AdminMapper;
import com.bnksystem.trainning1team.mapper.EquipMapper;
import com.bnksystem.trainning1team.type.EquipmentStatusType;
import com.bnksystem.trainning1team.type.EquipmentType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipService {

    private final EquipMapper equipMapper;
    private final AdminMapper adminMapper;

    public List<EquipResponse> getTotalEquipList() {
        return equipMapper.getTotalEquipList();
    }

    public List<AdminEquipmentDtoResponse> getAdminEquipmentList() {
        List<AdminEquipmentDto> list = adminMapper.selectEquipmentListAll();

        List<AdminEquipmentDtoResponse> adminEquipmentDtoResponseList = new ArrayList<>();

        for(AdminEquipmentDto data : list){
            adminEquipmentDtoResponseList.add(new AdminEquipmentDtoResponse(data));
        }

        return adminEquipmentDtoResponseList;
    }

    public AdminEquipmentDtoResponse getEquipmentInfo(String serialNo) {
        AdminEquipmentDto equipmentDto = adminMapper.selectEquipMentInfo(serialNo);

        AdminEquipmentDtoResponse info = new AdminEquipmentDtoResponse(equipmentDto);
        return info;
    }

    public List<EquipmentStatusResponse> getEquipmentStatusList() {
        return EquipmentStatusResponse.toEquipmentStatusResponse();
    }

    public List<EquipmentTypeResponse> getEquipmentTypeList() {
        return EquipmentTypeResponse.toEquipmentTypeResponse();
    }

    public void updateEquipment(String serialNo, ModifyRequest modifyRequest) {
        modifyRequest.setOriginSerialNo(serialNo);
        modifyRequest.setStatusId(
                EquipmentStatusType.valueOf(modifyRequest.getEqStatus()).getStatusCode()
                );
        modifyRequest.setTypeId(EquipmentType.valueOf(modifyRequest.getEqType()).getStatusCode());

        adminMapper.updateEquipment(modifyRequest);
    }

    public void deleteEquipment(String serialNo) {
        adminMapper.deleteEquipment(serialNo);
    }
}
