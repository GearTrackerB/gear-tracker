package com.bnksystem.trainning1team.service;

import com.bnksystem.trainning1team.dto.Equip.EquipRentalStatusResponse;
import com.bnksystem.trainning1team.mapper.EquipMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipService {

    private final EquipMapper equipMapper;

    public List<EquipRentalStatusResponse> getTotalEquipList() {

        return equipMapper.getTotalEquipList();
    }
}
