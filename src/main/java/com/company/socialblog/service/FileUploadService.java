package com.company.socialblog.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileUploadService {

    String path = "C:\\Users\\Arda\\Desktop\\";

    public void uploadFile(MultipartFile file) {

        if (file.isEmpty()) {
            System.out.println("Failed to store empty file!");
        }
        try {
            var fileName = file.getOriginalFilename();
            System.out.println(fileName);
            var inputStream = file.getInputStream();
            System.out.println(inputStream);
            Files.copy(inputStream, Paths.get(path + fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("failed to store file");
        }
    }
}
