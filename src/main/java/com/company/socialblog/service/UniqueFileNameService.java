package com.company.socialblog.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
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

    public String getPrefixForDBFileName() {
        Calendar cal = Calendar.getInstance();
        LocalDateTime time = LocalDateTime.ofInstant(cal.toInstant(), ZoneId.systemDefault());

        int year = time.getYear();
        int month = time.getMonthValue();
        int day = time.getDayOfMonth();

        String dbFileName = year + "/" + month + "/" + day + "/";
        return dbFileName;
    }

    public String getPrefixForFilePAth() {
        Calendar cal = Calendar.getInstance();
        LocalDateTime time = LocalDateTime.ofInstant(cal.toInstant(), ZoneId.systemDefault());

        int year = time.getYear();
        int month = time.getMonthValue();
        int day = time.getDayOfMonth();

        String filePath = year + "/" + month + "/" + day + "/";
        return filePath;
    }
}
