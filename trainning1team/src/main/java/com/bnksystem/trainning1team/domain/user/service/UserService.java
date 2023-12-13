package com.bnksystem.trainning1team.domain.user.service;

import com.bnksystem.trainning1team.domain.user.entity.User;
import com.bnksystem.trainning1team.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
//transactional read only
//import springframe work로
@Transactional(readOnly = true)
public class UserService {

    // 레포지토리 호출
    private final UserRepository userRepository;

    @Transactional
    public User createUser(String name) {
        // DB SEQ 연동
        final User user = User.builder()
                .name(name)
                        .build();

        // JAP Repository의 Save 기능 사용
        userRepository.save(user);

        // User 조회
        return userRepository.findBySeq(user.getSeq());
    }
}
