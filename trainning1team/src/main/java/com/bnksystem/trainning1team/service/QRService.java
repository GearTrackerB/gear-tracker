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

@Service
@RequiredArgsConstructor
public class QRService {

    private final QRMapper qrMapper;
    private final MemberMapper memberMapper;

    @Transactional
    public void checkout(QRRequest qrRequest) {
        EquipmentStatus status = qrMapper.selectEquipmentStatus(qrRequest); //장비 조회

        if(status.getStatusId() != EquipmentStatusType.출고예정.getStatusCode()){ //출고예정 상태가 아니라면, 에러발생
            //tb_equipments 테이블의 상태로 장비 상태 관리중(tb_entry_exit_record 테이블의 장비 상태와는 무관)
            throw new CustomException(ErrorCode.CHECKOUT_FAIL);
        }

        MemberInfoDto admin = memberMapper.selectMemberInfo(qrRequest);

        qrMapper.updateEquipmentStatus(status.toChangeEquipmentStatusDto(admin)); //장비 상태를 출고예정 상태로 변경
        qrMapper.insertEntryExitRecordQR(status.toRecordDto()); //출고 상태 기록
    }

    @Transactional
    public void checkin(QRRequest qrRequest) {
        EquipmentStatus status = qrMapper.selectEquipmentStatus(qrRequest);

        if(status.getStatusId() != EquipmentStatusType.반납예정.getStatusCode()){ //반납예정 상태가 아니라면, 에러발생
            throw new CustomException(ErrorCode.CHECKIN_FAIL);
        }

        MemberInfoDto admin = memberMapper.selectMemberInfo(qrRequest);

        qrMapper.updateEquipmentStatus(status.toChangeEquipmentStatusDto(admin)); //장비 상태를 반납 상태로 변경
        qrMapper.insertEntryExitRecordQR(status.toRecordDto()); //반납 상태 기록
    }

    @Transactional
    public void inspect(QRRequest qrRequest) {
        EquipmentStatus status = qrMapper.selectEquipmentStatus(qrRequest);

        if(status.getCompleteYn() == 'Y'){ //이미 재고 조사를 했다면, 에러 발생
            throw new CustomException(ErrorCode.ALREADY_INSPECTED);
        }

        qrMapper.updateInspectionComplete(status);

        MemberInfoDto admin = memberMapper.selectMemberInfo(qrRequest);
        qrMapper.insertInspectRecord(new InspectorRecordDto(status.getEqId(), admin.getId()));
    }
}
