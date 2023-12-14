package com.bnksystem.trainning1team.domain.user.controller;

import com.bnksystem.trainning1team.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @PostMapping("/login")
    public String createUser(@RequestParam(name="userName")String userName) {
        // 비즈니스 로직 호출
        return "hello";
    }

    @GetMapping("/test")
    public String test(){
        userService.test();
        return "test 성공";
    }
}
