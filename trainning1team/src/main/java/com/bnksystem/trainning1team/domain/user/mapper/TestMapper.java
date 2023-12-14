package com.bnksystem.trainning1team.domain.user.mapper;

import com.bnksystem.trainning1team.domain.user.TestDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface TestMapper {
    List<TestDto> selectAll();
}
