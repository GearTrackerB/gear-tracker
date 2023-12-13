package com.bnksystem.trainning1team.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity // DB 생성 작업
@Getter // @DATA는 지양
@NoArgsConstructor(access = AccessLevel.PROTECTED) // ???
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_seq")
    private Long seq;

    @Column(name = "user_name")
    private String name;

    // 객체 생성 시 사용할 빌더 만들기
    @Builder
    public User(String name) {
        this.name = name;
    }

}

// 테이블, 커럼 등 생성하면 만들어 질 것
// Dto는 DB 데이터 가공해서 넣어 주기
