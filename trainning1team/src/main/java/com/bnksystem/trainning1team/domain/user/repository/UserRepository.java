package com.bnksystem.trainning1team.domain.user.repository;

import com.bnksystem.trainning1team.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

// JPA 연결
public interface UserRepository extends JpaRepository<User, Long> { // 테이블 명, 기본 키

    User findBySeq(Long seq);

    User findByName(String name);

}
