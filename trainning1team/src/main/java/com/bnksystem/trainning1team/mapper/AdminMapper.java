package com.bnksystem.trainning1team.mapper;

import com.bnksystem.trainning1team.dto.Equip.AdminEquipmentDto;
import com.bnksystem.trainning1team.dto.Equip.ModifyRequest;
import com.bnksystem.trainning1team.dto.Equip.RegistRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface AdminMapper {
    List<AdminEquipmentDto> selectEquipmentListAll();

    AdminEquipmentDto selectEquipMentInfo(String serialNo);

    void updateEquipment(ModifyRequest modifyRequest);

    void deleteEquipment(String serialNo);

    void insertEquipment(RegistRequest registRequest);

    void insertEntryExitRecordToStatusOne(RegistRequest registRequest);

    List<HashMap<String, Object>> selectEquipmentList();
}
