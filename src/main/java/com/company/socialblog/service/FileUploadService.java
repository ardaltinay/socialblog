package com.company.socialblog.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;

@Service
public class FileUploadService {

    String path = "C:\\Users\\Arda\\Desktop\\repositories\\socialblog\\src\\main\\resources\\static\\uploads\\";

    public String uploadFile(MultipartFile file, String newFileName) throws IOException {

        Calendar cal = Calendar.getInstance();
        LocalDateTime time = LocalDateTime.ofInstant(cal.toInstant(), ZoneId.systemDefault());

        int year = time.getYear();
        int month = time.getMonthValue();
        int day = time.getDayOfMonth();
        new File(path + year + "\\" + month + "\\" + day).mkdirs();
        File dir = new File(path + year + "\\" + month + "\\" + day);

        if (file.isEmpty()) {
            System.out.println("Failed to store empty file!");
        }

        file.transferTo(Paths.get(dir + "\\" + newFileName));

        String result = year + "\\" + month + "\\" + day + "\\";
        return result;
    }
}
