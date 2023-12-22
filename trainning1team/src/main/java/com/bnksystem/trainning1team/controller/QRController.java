package com.bnksystem.trainning1team.controller;

import com.bnksystem.trainning1team.dto.QR.QRRequest;
import com.bnksystem.trainning1team.dto.Response;
import com.bnksystem.trainning1team.service.QRService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public Response<?> checkout(@RequestPart QRRequest qrRequest,
                                @RequestPart("eqImage") MultipartFile eqImage){
        int res = qrService.checkout(qrRequest, eqImage);

        if(res == 1){
            return new Response(200, "출고 완료");
        }else{
            return new Response(499, "이미지 업로드 실패");
        }

    }

    /*
    * QR 반납 요청을 받습니다.
    * 장비의 상태를 확인해, 반납예정 상태를 반납으로 변경합니다.
    * */
    @PutMapping("/manager/equipment/checkin")
    @ResponseBody
    public Response<?> checkin(@RequestPart QRRequest qrRequest,
                               @RequestPart("eqImage") MultipartFile eqImage){
        int res = qrService.checkin(qrRequest, eqImage);

        if(res == 1){
            return new Response(200, "반납 완료");
        }else{
            return new Response(499, "이미지 업로드 실패");
        }
    }

    /*
    * QR 재고 조사 요청을 받습니다.
    * 장비를 조회해, 재고 조사 로직을 수행합니다.
    * */
    @PostMapping("/manager/equipment")
    @ResponseBody
    public Response<?> inspect(@RequestPart QRRequest qrRequest,
                               @RequestPart("eqImage") MultipartFile eqImage){
        int res = qrService.inspect(qrRequest, eqImage);

        if(res == 1){
            return new Response(200, "재고 조사 완료");
        }else{
            return new Response(499, "이미지 업로드 실패");
        }
    }
}
