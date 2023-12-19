package com.bnksystem.trainning1team.service;

import com.bnksystem.trainning1team.dto.Equip.AdminEquipmentDto;
import com.bnksystem.trainning1team.dto.Equip.EquipResponse;
import com.bnksystem.trainning1team.dto.Equip.AdminEquipmentDtoResponse;
import com.bnksystem.trainning1team.mapper.AdminMapper;
import com.bnksystem.trainning1team.mapper.EquipMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
}
