package com.bnksystem.trainning1team.mapper;

import com.bnksystem.trainning1team.dto.Member.*;
import com.bnksystem.trainning1team.dto.QR.QRRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {
    LoginResponse memberLogin(LoginRequest loginRequest);

    void insertMember(JoinRequest joinRequest);

    MemberInfoDto selectMemberInfo(String empNo);

    List<MemberEmpNoResponse> selectEmpNoList();
}
