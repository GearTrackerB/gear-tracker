package com.bnksystem.trainning1team.service;

import com.bnksystem.trainning1team.dto.Equip.InspectorRecordDto;
import com.bnksystem.trainning1team.dto.Member.MemberInfoDto;
import com.bnksystem.trainning1team.dto.QR.EquipmentStatus;
import com.bnksystem.trainning1team.dto.QR.QRRequest;
import com.bnksystem.trainning1team.handler.CustomException;
import com.bnksystem.trainning1team.handler.error.ErrorCode;
import com.bnksystem.trainning1team.mapper.MemberMapper;
import com.bnksystem.trainning1team.mapper.QRMapper;
import com.bnksystem.trainning1team.type.EquipmentStatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class QRService {

    private final QRMapper qrMapper;
    private final MemberMapper memberMapper;
    private final FileService fileService;

    @Transactional
    public int checkout(QRRequest qrRequest, MultipartFile eqImage) {
        EquipmentStatus status = qrMapper.selectEquipmentStatus(qrRequest.getSerialNo()); //장비 조회

        if(status.getStatusId() != EquipmentStatusType.출고예정.getStatusCode()){ //출고예정 상태가 아니라면, 에러발생
            //tb_equipments 테이블의 상태로 장비 상태 관리중(tb_entry_exit_record 테이블의 장비 상태와는 무관)
            throw new CustomException(ErrorCode.CHECKOUT_FAIL);
        }

        MemberInfoDto admin = memberMapper.selectMemberInfo(qrRequest.getEmpNo());

        //이미지 업로드
        String url = "";
        try{
            url = fileService.imageUpload(eqImage);
        }catch (Exception e){
            return 0;
        }

        qrMapper.updateEquipmentStatus(status.toChangeEquipmentStatusDto(admin)); //장비 상태를 출고예정 상태로 변경
        qrMapper.insertEntryExitRecordQR(status.toRecordDto(url)); //출고 상태 기록

        return 1;
    }

    @Transactional
    public int checkin(QRRequest qrRequest, MultipartFile eqImage) {
        EquipmentStatus status = qrMapper.selectEquipmentStatus(qrRequest.getSerialNo());

        if(status.getStatusId() != EquipmentStatusType.반납예정.getStatusCode()){ //반납예정 상태가 아니라면, 에러발생
            throw new CustomException(ErrorCode.CHECKIN_FAIL);
        }

        MemberInfoDto admin = memberMapper.selectMemberInfo(qrRequest.getEmpNo());

        //이미지 업로드
        String url = "";
        try{
            url = fileService.imageUpload(eqImage);
        }catch (Exception e){
            return 0;
        }

        qrMapper.updateEquipmentStatus(status.toChangeEquipmentStatusDto(admin)); //장비 상태를 반납 상태로 변경
        qrMapper.insertEntryExitRecordQR(status.toRecordDto(url)); //반납 상태 기록

        return 1;
    }

    @Transactional
    public int inspect(QRRequest qrRequest, MultipartFile eqImage) {
        EquipmentStatus status = qrMapper.selectEquipmentStatus(qrRequest.getSerialNo());
        if(status.getCompleteYn() != 'N'){ //이미 재고 조사를 했다면, 에러 발생
            throw new CustomException(ErrorCode.ALREADY_INSPECTED);
        }

        //이미지 업로드
        String url = "";
        try{
            url = fileService.imageUpload(eqImage);
        }catch (Exception e){
            return 0;
        }

        qrMapper.updateInspectionComplete(status);

        MemberInfoDto admin = memberMapper.selectMemberInfo(qrRequest.getEmpNo());
        qrMapper.insertInspectRecord(new InspectorRecordDto(status.getEqId(), admin.getId(), url));

        return 1;
    }
}
