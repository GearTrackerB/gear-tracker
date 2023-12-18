package com.bnksystem.trainning1team.controller;

import com.bnksystem.trainning1team.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    /*
    *   test용 API
    *   1. DB 접근
    *   2. 리턴 확인
    * */
    @ResponseBody
    @GetMapping("/test")
    public String test(){
        testService.test();
        return "test 성공";
    }

    @GetMapping("/test-main")
    public String testMain(){
        return "main";
    }
}
