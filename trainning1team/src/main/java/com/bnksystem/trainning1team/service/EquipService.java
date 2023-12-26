package com.bnksystem.trainning1team.service;


import com.bnksystem.trainning1team.dto.Equip.EquipDetailResponse;
import com.bnksystem.trainning1team.dto.Equip.EquipsListResponse;
import com.bnksystem.trainning1team.dto.Equip.RentalStatusResponse;
import com.bnksystem.trainning1team.handler.CustomException;
import com.bnksystem.trainning1team.handler.error.ErrorCode;
import com.bnksystem.trainning1team.dto.Equip.AdminEquipmentDto;
import com.bnksystem.trainning1team.dto.Equip.AdminEquipmentDtoResponse;
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
import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final FileService fileService;

    // 장비 출납 현황 리스트 반환
    public EquipsListResponse getRentalEquipList(int index, int size) {
        List<RentalStatusResponse> rentalStatusList = equipMapper.selectRentalEquipList(index, size);

        // 반환결과 null 이면 -1, 아니면 마지막 id를 lastIdx에 입력
        EquipsListResponse response = new EquipsListResponse();
        if (rentalStatusList.isEmpty()) {
            response.setLastIdx(-1L);
        } else {
            response.setLastIdx(rentalStatusList.get(rentalStatusList.size() - 1).getId());
        }
        // 조회 List 입력
        response.setEquipList(rentalStatusList);
        return response;
    }

    // 현재 재물조사 대상 장비 리스트 반환
    public EquipsListResponse getInventoryEquipList(int index, int size) {
        List<RentalStatusResponse> rentalStatusList = equipMapper.selectInventoryEquipList(index, size);

        // 반환결과 null 이면 -1, 아니면 마지막 id를 lastIdx에 입력
        EquipsListResponse response = new EquipsListResponse();
        if (rentalStatusList.isEmpty()) {
            response.setLastIdx(-1L);
        } else {
            response.setLastIdx(rentalStatusList.get(rentalStatusList.size() - 1).getId());
        }
        // 조회 List 입력
        response.setEquipList(rentalStatusList);
        return response;
    }




    // 장비 정보 상세 조회
    public EquipDetailResponse getEquipDetail(String serialNO) {
        EquipDetailResponse equipDetail = equipMapper.selectEquipDetail(serialNO);
        if(equipDetail == null) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        } else {
            return equipDetail;
        }
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

        //QR 생성
        String QrImageUrl = "";
        try{
            QrImageUrl = fileService.makeQrCode(modifyRequest.getSerialNo());
        }catch (Exception e){
            throw new CustomException(ErrorCode.UPDATE_FAIL);
        }
        modifyRequest.setQrImageUrl(QrImageUrl);

        //상태 변경
        adminMapper.updateEquipment(modifyRequest);

        //장비 출납 기록부에 변경사항 반영
        EquipmentStatus status = qrMapper.selectEquipmentStatus(modifyRequest.getSerialNo());
        RecordDto recordDto = new RecordDto(status.getEqId(), modifyRequest.getStatusId(),status.getMemberId());

        qrMapper.insertEntryExitRecordQRAdmin(recordDto);
    }

    public void deleteEquipment(String serialNo) {
        adminMapper.deleteEquipment(serialNo);
    }

    @Transactional
    public void registEquipment(RegistRequest registRequest) {
        MemberInfoDto memberInfoDto = memberMapper.selectMemberInfo(registRequest.getEmpNo());

        registRequest.setStatusId(EquipmentStatusType.출고예정.getStatusCode());
        registRequest.setTypeId(EquipmentType.valueOf(registRequest.getEqType()).getStatusCode());

        String QrImageUrl = "";
        try{
            QrImageUrl = fileService.makeQrCode(registRequest.getSerialNo());
        }catch (Exception e){
            throw new CustomException(ErrorCode.REGIST_FAIL);
        }
        registRequest.setQrImageUrl(QrImageUrl);
        adminMapper.insertEquipment(registRequest);

        System.out.println("장비 ID 확인 " + registRequest.getEqId());

        registRequest.setEmpId(memberInfoDto.getId());
        //데이터 쌓아주기(출고 예정으로)
        adminMapper.insertEntryExitRecordToStatusOne(registRequest);
    }

    public List<HashMap<String, Object>> getEquipmentExcelList() {
        return adminMapper.selectEquipmentList();
    }

    public int registExcel(HashMap<String, Object> parameters) {

        RegistRequest registRequest = new RegistRequest();
        //"serialNo", "eqType", "eqNm", "eqModel", "eqMaker", "empNo"
        registRequest.setSerialNo((String) parameters.get("serialNo"));
        registRequest.setEqType((String) parameters.get("eqType"));
        registRequest.setEqNm((String) parameters.get("eqNm"));
        registRequest.setEqModel((String) parameters.get("eqModel"));
        registRequest.setEqMaker((String) parameters.get("eqMaker"));
        registRequest.setEmpNo((String) parameters.get("empNo"));

        try{
            registEquipment(registRequest);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }

        return 1;
    }
}
