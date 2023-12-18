package com.bnksystem.trainning1team.controller;


import com.bnksystem.trainning1team.dto.Equip.EquipResponse;
import com.bnksystem.trainning1team.dto.Response;
import com.bnksystem.trainning1team.service.EquipService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class EquipController {

    private final EquipService equipService;

    // todo param 결정
    @GetMapping("/manager/equipment/{index}/{size}")
    public Response<List<EquipResponse>> getTotalEquipList(@PathVariable Long index, int size) {
        List<EquipResponse> list = equipService.getTotalEquipList();
        return new Response(200, "조회 성공", list);
    }
}
