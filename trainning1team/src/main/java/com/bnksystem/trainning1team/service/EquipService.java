package com.bnksystem.trainning1team.service;

import com.bnksystem.trainning1team.dto.Equip.EquipRentalStatusResponse;
import com.bnksystem.trainning1team.dto.Equip.EquipsListResponse;
import com.bnksystem.trainning1team.dto.Equip.RentalStatusResponse;
import com.bnksystem.trainning1team.mapper.EquipMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipService {

    private final EquipMapper equipMapper;

    public List<EquipRentalStatusResponse> getTotalEquipList() {

        return equipMapper.getTotalEquipList();
    }

    public EquipsListResponse getEquipments(int index, int size) {
        List<RentalStatusResponse> rentalStatusList = equipMapper.getEquipments(index, size);

        EquipsListResponse response = new EquipsListResponse();

        if (rentalStatusList.isEmpty()) {
            response.setLastIdx(-1L);
        } else {
            response.setLastIdx(rentalStatusList.get(rentalStatusList.size() - 1).getId());
        }

        response.setEquipList(rentalStatusList);
        return response;
    }
}
