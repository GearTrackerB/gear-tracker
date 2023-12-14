package com.bnksystem.trainning1team.domain.user.service;

import com.bnksystem.trainning1team.domain.user.TestDto;
import com.bnksystem.trainning1team.domain.user.mapper.TestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final TestMapper testMapper;

    public void test(){
        List<TestDto> res = testMapper.selectAll();

        for(TestDto test1 : res){
            System.out.println(test1.toString());
        }
    }
}
