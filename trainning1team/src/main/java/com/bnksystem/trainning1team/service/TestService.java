package com.bnksystem.trainning1team.service;

import com.bnksystem.trainning1team.dto.TestDto;
import com.bnksystem.trainning1team.mapper.TestMapper;
import com.bnksystem.trainning1team.handler.CustomException;
import com.bnksystem.trainning1team.handler.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TestService {

    private final TestMapper testMapper;

    public void test(){
        List<TestDto> res = testMapper.selectAll();

//        throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);

        for(TestDto test1 : res){
            System.out.println(test1.toString());
        }
    }
}
