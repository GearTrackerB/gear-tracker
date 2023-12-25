package com.bnksystem.trainning1team.service;

import com.bnksystem.trainning1team.util.DateUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class FileService {
    @Value("${com.upload.path}")
    private String uploadBasePath;
    @Value("${com.upload.url}")
    private String uploadBaseUrl;

    public String imageUpload(MultipartFile uploadImage) throws IOException {
        String baseUploadPath = uploadBasePath + "/" + DateUtils.getCurrentDate();
        String baseUploadUrl = uploadBaseUrl + "/" + DateUtils.getCurrentDate();

        File uploadPath = new File(baseUploadPath);

        if (!uploadPath.exists()) {
            boolean created = uploadPath.mkdirs();
            System.out.println("created : " + created);
        }

        if (uploadPath.exists()) {
            String fileUUID = String.valueOf(UUID.randomUUID());

            String originFileName = uploadImage.getOriginalFilename();
            String ext = originFileName.substring(originFileName.lastIndexOf('.') + 1);

            String filePath = baseUploadPath + "/" + fileUUID + "." + ext;
            String fileUrl = baseUploadUrl + "/" + fileUUID + "." + ext;

            FileOutputStream writer = new FileOutputStream(filePath);
            writer.write(uploadImage.getBytes());
            writer.close();

            return fileUrl;

        } else {
            return "0";
        }
    }

    public String makeQrCode(String serialNo) throws WriterException {

        int width = 200;
        int height = 200;

        String fileUUID = String.valueOf(UUID.randomUUID());

        String baseUploadPath = uploadBasePath + "/qr/" + DateUtils.getCurrentDate() +"/"+ fileUUID + ".png";
        String baseUploadUrl = uploadBaseUrl + "/qr/" + DateUtils.getCurrentDate()+"/"+ fileUUID + ".png";

        File file = new File(baseUploadPath);

        if (!file.exists()) {
            file.mkdirs();
        }

        BitMatrix encode = new MultiFormatWriter().encode(serialNo, BarcodeFormat.QR_CODE, width, height);

        try{
            MatrixToImageWriter.writeToPath(encode, "png", file.toPath());
            return baseUploadUrl;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
