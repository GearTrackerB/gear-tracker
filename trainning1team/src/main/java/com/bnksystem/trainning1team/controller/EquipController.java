package com.bnksystem.trainning1team.controller;


import com.bnksystem.trainning1team.dto.Equip.AdminEquipmentDto;
import com.bnksystem.trainning1team.dto.Equip.AdminEquipmentDtoResponse;
import com.bnksystem.trainning1team.dto.Equip.EquipResponse;
import com.bnksystem.trainning1team.dto.Response;
import com.bnksystem.trainning1team.service.EquipService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
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

    /*
    * main 페이지로 이동.
    * */
    @GetMapping("/admin/main-page")
    public String getAdminMainPage(HttpServletRequest request){

        return "main";
    }

    @PostMapping("/admin/getEquipmentList")
    @ResponseBody
    public Response<?> getEquipmentList(@RequestBody HashMap<String, Object> parameters){
        int page = (int) parameters.get("PAGE");
        int offset = (page - 1) * 5;

        List<AdminEquipmentDtoResponse> list = equipService.getAdminEquipmentList();
        return new Response(200, "SUCCESS", list);
    }
}
