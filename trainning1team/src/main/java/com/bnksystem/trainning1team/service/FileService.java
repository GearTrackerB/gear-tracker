package com.bnksystem.trainning1team.service;

import com.bnksystem.trainning1team.util.DateUtils;
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

}
