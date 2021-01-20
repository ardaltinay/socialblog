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
        String path = "D:\\social-blog-uploads\\";

        String filePath = uniqueFileNameService.getPrefixForFilePAth();

        new File(path + filePath).mkdirs();
        File dir = new File(path + filePath);
        if (file.isEmpty()) {
            System.out.println("Failed to store empty file!");
        }

        file.transferTo(Paths.get(dir  + newFileName));

        /*
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String mimeType = fileNameMap.getContentTypeFor(dir  + newFileName);
        System.out.println(mimeType);

        File f = new File(String.valueOf(Paths.get(dir  + newFileName)));
        String mimetype= new MimetypesFileTypeMap().getContentType(f);
        String type = mimetype.split("/")[0];
        System.out.println(type);
        if(type.equals("image"))
            System.out.println("It's an image");
        else
            System.out.println("It's NOT an image");
        */
    }
}
