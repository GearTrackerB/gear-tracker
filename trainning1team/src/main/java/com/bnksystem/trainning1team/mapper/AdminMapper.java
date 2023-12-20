package com.bnksystem.trainning1team.mapper;

import com.bnksystem.trainning1team.dto.Equip.AdminEquipmentDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {
    List<AdminEquipmentDto> selectEquipmentListAll();
}
