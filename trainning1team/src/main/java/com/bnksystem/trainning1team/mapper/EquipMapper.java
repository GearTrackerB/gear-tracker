package com.bnksystem.trainning1team.mapper;

import com.bnksystem.trainning1team.dto.Equip.EquipRentalStatusResponse;
import com.bnksystem.trainning1team.dto.Equip.RentalStatusResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EquipMapper {
    List<EquipRentalStatusResponse> getTotalEquipList();

    List<RentalStatusResponse> getEquipments(@Param("index") int index, @Param("size") int size);

}
