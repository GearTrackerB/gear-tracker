package com.bnksystem.trainning1team.controller;


import com.bnksystem.trainning1team.dto.Equip.*;
import com.bnksystem.trainning1team.dto.Member.MemberEmpNoResponse;
import com.bnksystem.trainning1team.dto.Response;
import com.bnksystem.trainning1team.service.EquipService;
import com.bnksystem.trainning1team.service.InspectionService;
import com.bnksystem.trainning1team.service.MemberService;
import com.bnksystem.trainning1team.util.ExcelType;
import com.bnksystem.trainning1team.util.ExcelUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.map.LinkedMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class EquipController {

    private final EquipService equipService;
    private final MemberService memberService;
    private final InspectionService inspectionService;

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

    /*
    * 장비 수정 요청을 받습니다.
    * serialNo에 해당하는 장비의 정보를 바꿉니다.
    * 장비 수정에 해당하는 record를 쌓아줘야합니다.
    * */
    @PostMapping("/admin/modify/{serialNo}")
    @ResponseBody
    public Response<?> equipmentModify(@PathVariable String serialNo, @RequestBody ModifyRequest modifyRequest){
        equipService.updateEquipment(serialNo, modifyRequest);
        return new Response<>(200, "SUCCESS");
    }

    /*
    * 장비 삭제 요청을 받습니다.
    * 실제 삭제는 하지 않고, use_yn 값을 N으로 변경합니다.
    * */
    @DeleteMapping("/admin/equipment/{serialNo}")
    @ResponseBody
    public Response<?> equipmentDelete(@PathVariable String serialNo){
        equipService.deleteEquipment(serialNo);
        return new Response<>(200, "SUCCESS");
    }

    /*
    * 장비 등록 페이지 요청을 받습니다.
    * 장비 등록에 필요한 정보인 회원 정보와 장비 타입 정보를 request 객체에 전달합니다.
    * */
    @RequestMapping("/admin/regist")
    public String createEquipmentPage(HttpServletRequest request){

        List<MemberEmpNoResponse> empList = memberService.selectMemberList();
        request.setAttribute("empList", empList);

        List<EquipmentTypeResponse> typeList = equipService.getEquipmentTypeList();
        request.setAttribute("typeList", typeList);

        return "regist";
    }

    /*
    * 장비 등록 요청을 받습니다.
    * 장비 등록시, 장비 상태를 출고 예정으로 변경하고, 출납 기록부에도 등록합니다.
    * */
    @PostMapping("/admin/regist/equipment")
    @ResponseBody
    public Response<?> createEquipment(@RequestBody RegistRequest registRequest){
        equipService.registEquipment(registRequest);
        return new Response<>(200, "SUCCESS");
    }

    /*
    * 장비 엑셀 업로드 요청을 받습니다.
    * */
    @PostMapping("/admin/registEquipmentExcel")
    @ResponseBody
    public Response<?> setEquipmentInfoFromExcel(
            @RequestParam("uploadFile") MultipartFile uploadFile) throws IOException {

        if (!uploadFile.isEmpty()) {
            String[] headers = { //식별코드, 제품종류, 제품명, 모델명, 제조사, 배정자
                    "serialNo", "eqType", "eqNm", "eqModel", "eqMaker", "empNo"
            };
            List<LinkedMap<String, Object>> datas = ExcelUtils.getInstance(ExcelType.XLSX).parse(headers, uploadFile.getInputStream(), 1);
            // 헤더, 파일, 인덱스 시작번호
            int successCnt = 0;
            int failCnt = 0;

            for (LinkedMap<String, Object> data : datas) {
                HashMap<String, Object> parameters = new HashMap<>();
                for (String key : headers) {
                    parameters.put(key, data.get(key));
                }

                int success = 0;

                success = equipService.registExcel(parameters);

                if (success > 0) {
                    successCnt++;
                } else {
                    failCnt++;
                }
            }

            HashMap<String, Object> resultMap = new HashMap<>();
            resultMap.put("successCnt", successCnt);
            resultMap.put("failCnt", failCnt);

            return new Response<>(200, "SUCCESS", resultMap);
        }else{
            return new Response<>(400, "파일이 없습니다");
        }
    }

    /*
    * 엑셀 다운로드 요청을 받습니다.
    * */
    @RequestMapping("/admin/download")
    public String excelReqFaultList(HttpServletResponse response) throws Exception {

        String[] header = new String[]{"식별코드", "제품 종류", "제품명", "모델명", "상태", "배정자", "최근 재물조사 일"};
        Integer[] contentSize = new Integer[]{6000, 4000, 4000, 4000, 4000, 4000, 4000};

        //String[] content = new String[] {"serialNo", "eqType", "eqNm", "eqModel", "eqStatus", "empNo", "regAt"};
        String[] content = new String[] {"serial_no", "eq_type", "eq_nm", "eq_model", "eq_status", "emp_no", "reg_at"};
        String fileName = "IT_Equipment_Status";

        List<HashMap<String, Object>> processList = equipService.getEquipmentExcelList();

        ExcelUtils.getInstance(ExcelType.XLSX).getExcel(header, contentSize, content, fileName, processList, response);
        //한글 헤더리스트, 컨텐츠 사이즈(컬럼 사이즈), 컨텐츠(영어 헤더), 파일 이름, 데이터(리스트 해시맵), response
        return "main";
    }

    @GetMapping("/admin/inspection")
    @ResponseBody
    public Response<?> setInspection(){
        inspectionService.updateInspection();

        return new Response<>(200, "SUCESS");
    }
}
