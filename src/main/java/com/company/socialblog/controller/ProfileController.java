package com.company.socialblog.controller;

import com.company.socialblog.entity.Picture;
import com.company.socialblog.entity.User;
import com.company.socialblog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ProfileController {

    public static final String USERNAME = "USERNAME";
    private UserService userService;
    private FileUploadService fileUploadService;
    private UniqueFileNameService fileNameService;
    private PictureService pictureService;
    private FileTypeControlService fileTypeControlService;
    private FindUserFromSessionService findUserService;

    @Autowired
    public ProfileController(UserService userService,
                             FileUploadService fileUploadService,
                             UniqueFileNameService fileNameService,
                             PictureService pictureService,
                             FileTypeControlService fileTypeControlService,
                             FindUserFromSessionService findUserService) {
        this.userService = userService;
        this.fileUploadService = fileUploadService;
        this.fileNameService = fileNameService;
        this.pictureService = pictureService;
        this.fileTypeControlService = fileTypeControlService;
        this.findUserService = findUserService;
    }

    @GetMapping("/profile")
    public String profilePageGet(Model model, HttpServletRequest request) {
        // session control
        User user = findUserService.findUser(request);
        if(user == null) {
            return "redirect:/login";
        }

        List<Picture> pictures = pictureService.getPictures();

        // add user attributes for template to model
        model.addAttribute(USERNAME, user.getUsername());
        model.addAttribute("userProfilePhoto", user.getProfilePhoto());
        model.addAttribute("userBiography", user.getBiography());
        model.addAttribute("userEmail", user.getEmail());
        model.addAttribute("pictures", pictures);

        return "profile";
    }

    @PostMapping("/profile")
    public String profilePagePost(Model model, HttpServletRequest request,
                  @RequestParam("upload-photo") MultipartFile uploadedPicture) {
        // get user
        User user = findUserService.findUser(request);

        // create a file name for database table
        String fileName = fileNameService.getUniqueFileName(uploadedPicture);
        String prefixFileName = fileNameService.getPrefixForDBFileName();

        // get input comment value from profile
        String commentPhoto = request.getParameter("photoComment");

        Picture picture = new Picture(prefixFileName + fileName, commentPhoto, user);
        pictureService.savePicture(picture);

        // file type control
        boolean isValidFileType = fileTypeControlService.fileTypeControl(uploadedPicture);
        if(isValidFileType) {
            try {
                fileUploadService.uploadFile(uploadedPicture, "\\" + fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "redirect:/profile";
        } else {
            model.addAttribute("fileTypeError", "Unsupported file format (Must be 'jpg', 'jpeg' or 'png')");
            return "profile";
        }
    }
}
