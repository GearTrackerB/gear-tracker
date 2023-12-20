package com.bnksystem.trainning1team.controller;


import com.bnksystem.trainning1team.dto.Equip.*;
import com.bnksystem.trainning1team.dto.Response;
import com.bnksystem.trainning1team.service.EquipService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
    public String getAdminMainPage(){

        return "main";
    }

    /*
    * 장비 리스트 리턴 (페이징 처리 전)
    * */
    @PostMapping("/admin/getEquipmentList")
    @ResponseBody
    public Response<?> getEquipmentList(@RequestBody HashMap<String, Object> parameters, HttpServletRequest request){
        int page = (int) parameters.get("PAGE");
        int offset = (page - 1) * 5;

        List<AdminEquipmentDtoResponse> list = equipService.getAdminEquipmentList();

        return new Response(200, "SUCCESS", list);
    }


    /*
    * modify 페이지로 연결
    * serialNo에 해당하는 장비 상세 정보 리턴
    * */
    @RequestMapping("/admin/modify/{serialNo}")
    public String equipmentInfo(HttpServletRequest request, @PathVariable String serialNo){

        AdminEquipmentDtoResponse info = equipService.getEquipmentInfo(serialNo);
        request.setAttribute("info", info);
        List<EquipmentStatusResponse> statusList = equipService.getEquipmentStatusList();
        List<EquipmentTypeResponse> typeList = equipService.getEquipmentTypeList();

        request.setAttribute("statusList", statusList);
        request.setAttribute("typeList", typeList);

        return "modify";
    }

    @PostMapping("/admin/modify/{serialNo}")
    @ResponseBody
    public Response<?> equipmentModify(@PathVariable String serialNo, @RequestBody ModifyRequest modifyRequest){
        equipService.updateEquipment(serialNo, modifyRequest);
        return new Response<>(200, "SUCCESS");
    }

    @DeleteMapping("/admin/equipment/{serialNo}")
    @ResponseBody
    public Response<?> equipmentDelete(@PathVariable String serialNo){
        equipService.deleteEquipment(serialNo);
        return new Response<>(200, "SUCCESS");
    }
}
