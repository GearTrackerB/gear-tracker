package com.bnksystem.trainning1team.service;

import com.bnksystem.trainning1team.dto.Equip.*;
import com.bnksystem.trainning1team.dto.Member.MemberInfoDto;
import com.bnksystem.trainning1team.dto.QR.EquipmentStatus;
import com.bnksystem.trainning1team.dto.QR.RecordDto;
import com.bnksystem.trainning1team.mapper.AdminMapper;
import com.bnksystem.trainning1team.mapper.EquipMapper;
import com.bnksystem.trainning1team.mapper.MemberMapper;
import com.bnksystem.trainning1team.mapper.QRMapper;
import com.bnksystem.trainning1team.type.EquipmentStatusType;
import com.bnksystem.trainning1team.type.EquipmentType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipService {

    private final EquipMapper equipMapper;
    private final AdminMapper adminMapper;
    private final MemberMapper memberMapper;
    private final QRMapper qrMapper;

    public List<EquipResponse> getTotalEquipList() {
        return equipMapper.getTotalEquipList();
    }

    public List<AdminEquipmentDtoResponse> getAdminEquipmentList() {
        List<AdminEquipmentDto> list = adminMapper.selectEquipmentListAll();

        List<AdminEquipmentDtoResponse> adminEquipmentDtoResponseList = new ArrayList<>();

        for(AdminEquipmentDto data : list){
            adminEquipmentDtoResponseList.add(new AdminEquipmentDtoResponse(data));
        }

        return adminEquipmentDtoResponseList;
    }

    public AdminEquipmentDtoResponse getEquipmentInfo(String serialNo) {
        AdminEquipmentDto equipmentDto = adminMapper.selectEquipMentInfo(serialNo);

        AdminEquipmentDtoResponse info = new AdminEquipmentDtoResponse(equipmentDto);
        return info;
    }

    public List<EquipmentStatusResponse> getEquipmentStatusList() {
        return EquipmentStatusResponse.toEquipmentStatusResponse();
    }

    public List<EquipmentTypeResponse> getEquipmentTypeList() {
        return EquipmentTypeResponse.toEquipmentTypeResponse();
    }

    @Transactional
    public void updateEquipment(String serialNo, ModifyRequest modifyRequest) {
        modifyRequest.setOriginSerialNo(serialNo);
        modifyRequest.setStatusId(
                EquipmentStatusType.valueOf(modifyRequest.getEqStatus()).getStatusCode()
                );
        modifyRequest.setTypeId(EquipmentType.valueOf(modifyRequest.getEqType()).getStatusCode());

        //상태 변경
        adminMapper.updateEquipment(modifyRequest);

        //장비 출납 기록부에 변경사항 반영
        EquipmentStatus status = qrMapper.selectEquipmentStatus(serialNo);
        RecordDto recordDto = new RecordDto(status.getEqId(), modifyRequest.getStatusId(),status.getMemberId());

        qrMapper.insertEntryExitRecordQR(recordDto);
    }

    public void deleteEquipment(String serialNo) {
        adminMapper.deleteEquipment(serialNo);
    }

    @Transactional
    public void registEquipment(RegistRequest registRequest) {
        MemberInfoDto memberInfoDto = memberMapper.selectMemberInfo(registRequest.getEmpNo());

        registRequest.setStatusId(EquipmentStatusType.출고예정.getStatusCode());
        registRequest.setTypeId(EquipmentType.valueOf(registRequest.getEqType()).getStatusCode());

        adminMapper.insertEquipment(registRequest);

        System.out.println("장비 ID 확인 " + registRequest.getEqId());

        registRequest.setEmpId(memberInfoDto.getId());
        //데이터 쌓아주기(출고 예정으로)
        adminMapper.insertEntryExitRecordToStatusOne(registRequest);
    }

    public List<HashMap<String, Object>> getEquipmentExcelList() {
        return adminMapper.selectEquipmentList();
    }
}
