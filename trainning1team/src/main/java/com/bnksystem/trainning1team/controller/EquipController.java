package com.bnksystem.trainning1team.controller;


import com.bnksystem.trainning1team.dto.Equip.EquipListResponse;
import com.bnksystem.trainning1team.dto.Equip.EquipRentalStatusResponse;
import com.bnksystem.trainning1team.dto.Equip.EquipsListResponse;
import com.bnksystem.trainning1team.dto.Response;
import com.bnksystem.trainning1team.service.EquipService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class EquipController {

    private final EquipService equipService;

//    // 장비출납현황조회 / list가 0 이면 start 0, 아니면 가장 앞의 idx 반환
//    @RequestMapping(value = "/manager/{index}/{size}", method = RequestMethod.GET)
//    public Response<?> getTotalEquipList(@PathVariable String index, @PathVariable int size) {
//        List<EquipRentalStatusResponse> list = equipService.getTotalEquipList();
//        EquipListResponse result = new EquipListResponse(
//                (list.isEmpty()) ? "0" : list.get(list.size()-1).serial_no, list
//        );
//        return new Response<EquipListResponse>(200, "조회 성공", result);
//    }

    @GetMapping("/manager/equipment")
    @ResponseBody
    public Response<EquipsListResponse> getEquipList(
            @RequestParam(defaultValue = "0") int index,
            @RequestParam(defaultValue = "10") int size) {

        EquipsListResponse equipListResponse = equipService.getEquipments(index, size);
        return new Response<>(200, "장비 출납 현황 조회 성공", equipListResponse);
    }
}
