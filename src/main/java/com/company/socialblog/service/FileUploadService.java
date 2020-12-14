package com.company.socialblog.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;



@Service
public class FileUploadService {

    String path = "C:\\Users\\Arda\\Desktop\\repositories\\socialblog\\src\\main\\resources\\static\\uploads\\";

    public void uploadFile(MultipartFile file, String newFileName, int year, int month, int day) throws IOException {
        String filePath = year + "\\" + month + "\\" + day + "\\";

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
            System.out.println("It's NOT an image");*/

    }

}
