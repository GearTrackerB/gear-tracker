package com.bnksystem.trainning1team.controller;

import com.bnksystem.trainning1team.dto.Member.LoginRequest;
import com.bnksystem.trainning1team.dto.Member.LoginResponse;
import com.bnksystem.trainning1team.dto.Response;
import com.bnksystem.trainning1team.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /*
    * member 로그인 요청을 받습니다.
    * 정상 요청이라면, 사원 번호를 리턴합니다.
    * */
    @PostMapping("/manager/login")
    @ResponseBody
    public Response<?> memberLogin(@RequestBody LoginRequest loginRequest){

        LoginResponse loginResponse = memberService.memberLogin(loginRequest);
        return new Response(200, "로그인 성공", loginResponse);
    }
}
