package com.bnksystem.trainning1team.mapper;

import com.bnksystem.trainning1team.dto.Equip.AdminEquipmentDto;
import com.bnksystem.trainning1team.dto.Equip.ModifyRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {
    List<AdminEquipmentDto> selectEquipmentListAll();

    AdminEquipmentDto selectEquipMentInfo(String serialNo);

    void updateEquipment(ModifyRequest modifyRequest);

    void deleteEquipment(String serialNo);
}
