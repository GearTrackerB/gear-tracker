package com.bnksystem.trainning1team.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InspectionMapper {

    void deleteInspection();

    void insertInspection();
}
