package com.bnksystem.trainning1team.mapper;

import com.bnksystem.trainning1team.dto.Equip.EquipRentalStatusResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EquipMapper {
    List<EquipRentalStatusResponse> getTotalEquipList();
}
