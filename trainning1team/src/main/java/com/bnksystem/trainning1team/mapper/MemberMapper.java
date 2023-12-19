package com.bnksystem.trainning1team.mapper;

import com.bnksystem.trainning1team.dto.Member.JoinRequest;
import com.bnksystem.trainning1team.dto.Member.LoginRequest;
import com.bnksystem.trainning1team.dto.Member.LoginResponse;
import com.bnksystem.trainning1team.dto.Member.MemberInfoDto;
import com.bnksystem.trainning1team.dto.QR.QRRequest;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    LoginResponse memberLogin(LoginRequest loginRequest);

    void insertMember(JoinRequest joinRequest);

    MemberInfoDto selectMemberInfo(QRRequest qrRequest);
}
