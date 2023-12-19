package com.bnksystem.trainning1team.mapper;

import com.bnksystem.trainning1team.dto.Equip.RentalStatusResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EquipMapper {
    // 장비 출납 현황 리스트 조회 / index 부터 / size 만큼
    List<RentalStatusResponse> getRentalEquipList(@Param("index") int index, @Param("size") int size);

}
