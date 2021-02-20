package com.company.socialblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Service
public class FileUploadService {

    @Autowired
    private UniqueFileNameService uniqueFileNameService;

    public void uploadFile(MultipartFile file, String newFileName) throws IOException {
        String path = "/home/arda/Desktop/social-blog-uploads/";

        String filePath = uniqueFileNameService.getPrefixForFilePAth();

        new File(path + filePath).mkdirs();
        File dir = new File(path + filePath);
        if (file.isEmpty()) {
            System.out.println("Failed to store empty file!");
        }

        file.transferTo(Paths.get(dir  + newFileName));
    }
}
