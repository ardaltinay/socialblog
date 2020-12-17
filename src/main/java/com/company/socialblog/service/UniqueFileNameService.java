package com.company.socialblog.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class UniqueFileNameService {

    public String getFileType(MultipartFile multipartFile) {

        String fileName = multipartFile.getOriginalFilename();
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);

        return fileType;
    }

    public String getUniqueFileName(MultipartFile multipartFile) {

        String fileName = multipartFile.getOriginalFilename();
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmssSSSS").format(new Date());
        String newFileName = timeStamp + "." + fileType;

        return newFileName;
    }
}
