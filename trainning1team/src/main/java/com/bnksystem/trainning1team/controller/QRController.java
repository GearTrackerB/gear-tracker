package com.bnksystem.trainning1team.controller;

import com.bnksystem.trainning1team.dto.QR.QRRequest;
import com.bnksystem.trainning1team.dto.Response;
import com.bnksystem.trainning1team.service.QRService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class QRController {

    private final QRService qrService;

    /*
    * QR 출고 요청을 받습니다.
    * 장비의 상태를 확인해, 출고 예정인 장비의 상태를 출고로 변경합니다.
    * */
    @PutMapping("/manager/equipment/checkout")
    @ResponseBody
    public Response<?> checkout(@RequestBody QRRequest qrRequest){
        qrService.checkout(qrRequest);

        return new Response(200, "출고 완료");
    }

    /*
    * QR 반납 요청을 받습니다.
    * 장비의 상태를 확인해, 반납예정 상태를 반납으로 변경합니다.
    * */
    @PutMapping("/manager/equipment/checkin")
    @ResponseBody
    public Response<?> checkin(@RequestBody QRRequest qrRequest){
        qrService.checkin(qrRequest);

        return new Response(200, "반납 완료");
    }
}
