package com.bnksystem.trainning1team.domain.user.controller;

// 유저로 부터 요청 받는 지점

import com.bnksystem.trainning1team.domain.user.entity.User;
import com.bnksystem.trainning1team.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // SpringWeb Annotation, Rest 통신 방식임을 명시
@RequestMapping("/user") // 여기 있는 루트는 모두 /user로 시작 함
@RequiredArgsConstructor // new로 객체 생성 하지 않고 final, not null 필드를 자동으로 생성
public class UserController {

    // 서비스 호출
    private final UserService userService;

    // /user/login
    @PostMapping("/login")
    public User createUser(@RequestParam(name="userName")String userName) {
        // 비즈니스 로직 호출
        return userService.createUser(userName);
    }
}
