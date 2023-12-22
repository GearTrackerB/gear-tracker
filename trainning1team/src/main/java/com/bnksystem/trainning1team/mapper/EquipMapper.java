package com.bnksystem.trainning1team.mapper;


import com.bnksystem.trainning1team.dto.Equip.EquipDetailResponse;
import com.bnksystem.trainning1team.dto.Equip.RentalStatusResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface EquipMapper {

    // 장비 출납 현황 리스트 조회 / index 부터 / size 만큼
    List<RentalStatusResponse> selectRentalEquipList(@Param("index") int index, @Param("size") int size);

    // 장비 상세 정보 조회 / serialNo인 장비
    EquipDetailResponse selectEquipDetail(@Param("serialNo") String serialNo);

    // 재물 조사 현황 리스트 조회 / index 부터 / size 만큼
    List<RentalStatusResponse> selectInventoryEquipList(@Param("index") int index, @Param("size") int size);

}
