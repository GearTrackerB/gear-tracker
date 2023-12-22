package com.bnksystem.trainning1team.service;

import com.bnksystem.trainning1team.dto.Member.JoinRequest;
import com.bnksystem.trainning1team.dto.Member.LoginRequest;
import com.bnksystem.trainning1team.dto.Member.LoginResponse;
import com.bnksystem.trainning1team.dto.Member.MemberEmpNoResponse;
import com.bnksystem.trainning1team.handler.CustomException;
import com.bnksystem.trainning1team.handler.error.ErrorCode;
import com.bnksystem.trainning1team.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;

    public LoginResponse memberLogin(LoginRequest loginRequest) {
        LoginResponse response = memberMapper.memberLogin(loginRequest);
        if(response == null){ // 로그인 실패 시, 400 에러 리턴.
            throw new CustomException(ErrorCode.LOGIN_ID_NOTFOUND);
        }

        return response;
    }

    public void join(JoinRequest joinRequest) {
        memberMapper.insertMember(joinRequest);
    }

    public List<MemberEmpNoResponse> selectMemberList() {
        return memberMapper.selectEmpNoList();
    }
}
