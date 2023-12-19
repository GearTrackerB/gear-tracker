package com.bnksystem.trainning1team.controller;


import com.bnksystem.trainning1team.dto.Equip.EquipsListResponse;
import com.bnksystem.trainning1team.dto.Response;
import com.bnksystem.trainning1team.service.EquipService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class EquipController {

    private final EquipService equipService;

    // 장비출납현황조회(마지막 index 이후, size 만큼, 마지막 index와 현황 리스트를 반환)
    @GetMapping("/manager/equipment")
    @ResponseBody
    public Response<EquipsListResponse> getRentalEquipList(
            @RequestParam(defaultValue = "0") int index,
            @RequestParam(defaultValue = "10") int size) {
        EquipsListResponse equipListResponse = equipService.getRentalEquipList(index, size);
        return new Response<>(200, "장비 출납 현황 조회 성공", equipListResponse);
    }
}
