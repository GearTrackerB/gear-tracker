package com.bnksystem.trainning1team.service;


import com.bnksystem.trainning1team.dto.Equip.EquipDetailResponse;
import com.bnksystem.trainning1team.dto.Equip.EquipsListResponse;
import com.bnksystem.trainning1team.dto.Equip.RentalStatusResponse;
import com.bnksystem.trainning1team.handler.CustomException;
import com.bnksystem.trainning1team.handler.error.ErrorCode;
import com.bnksystem.trainning1team.dto.Equip.AdminEquipmentDto;
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

    // 장비 출납 현황 리스트 반환
    public EquipsListResponse getRentalEquipList(int index, int size) {
        List<RentalStatusResponse> rentalStatusList = equipMapper.getRentalEquipList(index, size);

        // 반환결과 null 이면 -1, 아니면 마지막 id를 lastIdx에 입력
        EquipsListResponse response = new EquipsListResponse();
        if (rentalStatusList.isEmpty()) {
            response.setLastIdx(-1L);
        } else {
            response.setLastIdx(rentalStatusList.get(rentalStatusList.size() - 1).getId());
        }
        // 조회 List 입력
        response.setEquipList(rentalStatusList);
        return response;
    }

    public EquipDetailResponse getEquipDetail(String serialNO) {
        EquipDetailResponse equipDetail = equipMapper.selectEquipDetail(serialNO);
        if(equipDetail == null) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        } else {
            return equipDetail;
        }
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
