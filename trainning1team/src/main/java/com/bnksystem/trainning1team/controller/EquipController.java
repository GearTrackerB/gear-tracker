package com.bnksystem.trainning1team.controller;



import com.bnksystem.trainning1team.dto.Equip.EquipDetailResponse;
import com.bnksystem.trainning1team.dto.Equip.EquipsListResponse;
import com.bnksystem.trainning1team.dto.Equip.AdminEquipmentDtoResponse;
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

    // 장비출납현황조회(마지막 index 이후, size 만큼, 마지막 index와 현황 리스트를 반환)
    @GetMapping("/manager/equipment")
    @ResponseBody
    public Response<EquipsListResponse> getRentalEquipList(
            @RequestParam(defaultValue = "0") int index,
            @RequestParam(defaultValue = "10") int size) {
        EquipsListResponse equipListResponse = equipService.getRentalEquipList(index, size);
        return new Response<>(200, "장비 출납 현황 조회 성공", equipListResponse);
    }

    // 장비상세조회(serial로 해당 장비의 정보가 담긴 객체 반환)
    @GetMapping("/manager/equipment")
    @ResponseBody
    public Response<EquipDetailResponse> getEquipDetail(
            @RequestParam(defaultValue = "none") String serialNo) {
        EquipDetailResponse equipDetail = equipService.getEquipDetail(serialNo);
        return new Response<>(200, "장비 상세 조회 성공", equipDetail);
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
