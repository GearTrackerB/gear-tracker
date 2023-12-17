package com.bnksystem.trainning1team.mapper;

import com.bnksystem.trainning1team.dto.TestDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TestMapper {
    List<TestDto> selectAll();
}
