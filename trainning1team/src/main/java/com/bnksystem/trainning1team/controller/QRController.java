package com.bnksystem.trainning1team.controller;

import com.bnksystem.trainning1team.dto.QR.QRRequest;
import com.bnksystem.trainning1team.dto.Response;
import com.bnksystem.trainning1team.service.QRService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.util.UUID;

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

    // QR 코드 생성 테스트
    @GetMapping("/qr/test")
    @ResponseBody
    public ResponseEntity<byte[]> qrCode() throws WriterException {
        int width = 200;
        int height = 200;
        String fileUUID = String.valueOf(UUID.randomUUID());

        String filePath = "/home/bnksys/www/upload/qr" + "/" + fileUUID + ".png";
        String fileUrl = "/upload/qr" + "/" + fileUUID + ".png";

        File file = new File(filePath);
        String data = "hello";
        if (!file.exists()) {
            boolean created = file.mkdirs();
            System.out.println("created : " + created);
        }

        System.out.println(file.exists());

        BitMatrix encode = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, width, height);

        try{
            MatrixToImageWriter.writeToPath(encode, "png", file.toPath());
            return null;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }
}
