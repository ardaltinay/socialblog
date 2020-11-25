package com.company.socialblog.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Service
public class FileUploadService {

    String path = "C:\\Users\\Arda\\Desktop\\repositories\\socialblog\\src\\main\\resources\\static\\uploads\\";

    public void uploadFile(MultipartFile file, String newFileName) throws IOException {
        File dir = new File("2020\\");
        System.out.println(dir);
        boolean value = dir.mkdirs();
        if (value) {
            System.out.println("The new directory is created.");
        } else {
            System.out.println("The directory already exists.");
        }

        if (file.isEmpty()) {
            System.out.println("Failed to store empty file!");
        }
        file.transferTo(Paths.get(path + dir + newFileName));

    }
}
