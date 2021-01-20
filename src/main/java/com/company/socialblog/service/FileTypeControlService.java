package com.company.socialblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileTypeControlService {
    @Autowired
    private UniqueFileNameService fileNameService;

    public boolean fileTypeControl(MultipartFile uploadedPicture) {

        String fileType = fileNameService.getFileType(uploadedPicture);
        if (fileType.equals("jpg") || fileType.equals("jpeg") || fileType.equals("png")) {
            return true;
        } else {
            return false;
        }
    }
}
