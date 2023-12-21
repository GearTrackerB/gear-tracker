package com.bnksystem.trainning1team.service;

import com.bnksystem.trainning1team.mapper.InspectionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InspectionService {

    private final InspectionMapper inspectionMapper;

    public void updateInspection() {
        inspectionMapper.deleteInspection();
        inspectionMapper.insertInspection();
    }
}
