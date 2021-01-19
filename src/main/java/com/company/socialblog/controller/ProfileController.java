package com.company.socialblog.controller;

import com.company.socialblog.entity.Picture;
import com.company.socialblog.entity.User;
import com.company.socialblog.service.FileUploadService;
import com.company.socialblog.service.PictureService;
import com.company.socialblog.service.UniqueFileNameService;
import com.company.socialblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;

@Controller
public class ProfileController {

    public static final String USERNAME = "USERNAME";
    private UserService userService;
    private FileUploadService fileUploadService;
    private UniqueFileNameService fileNameService;
    private PictureService pictureService;

    @Autowired
    public ProfileController(UserService userService,
                             FileUploadService fileUploadService,
                             UniqueFileNameService fileNameService,
                             PictureService pictureService) {
        this.userService = userService;
        this.fileUploadService = fileUploadService;
        this.fileNameService = fileNameService;
        this.pictureService = pictureService;
    }

    @GetMapping("/profile")
    public String profilePageGet(Model model, HttpServletRequest request) {
        // Session
        String sessionUsername = (String) request.getSession().getAttribute(USERNAME);
        if (sessionUsername == null) {
            return "redirect:/login";
        }
        // get user from session
        User user = userService.findByUsername(sessionUsername);

        // add user attributes for template to model
        model.addAttribute(USERNAME, sessionUsername);
        model.addAttribute("userProfilePhoto", user.getProfilePhoto());
        model.addAttribute("userBiography", user.getBiography());

        return "profile";
    }

    @PostMapping("/profile")
    public String profilePagePost(Model model, HttpServletRequest request,
                  @RequestParam("upload-photo") MultipartFile uploadedPicture) {
        // Session
        String sessionUsername = (String) request.getSession().getAttribute(USERNAME);
        if (sessionUsername == null) {
            return "redirect:/login";
        }
        // get user from session
        User user = userService.findByUsername(sessionUsername);
        System.out.println(user);

        String fileType = fileNameService.getFileType(uploadedPicture);

        Calendar cal = Calendar.getInstance();
        LocalDateTime time = LocalDateTime.ofInstant(cal.toInstant(), ZoneId.systemDefault());

        String fileName = fileNameService.getUniqueFileName(uploadedPicture);
        int year = time.getYear();
        int month = time.getMonthValue();
        int day = time.getDayOfMonth();

        // create a file name for database table
        String dbFileName = year + "/" + month + "/" + day + "/" + fileName;
        System.out.println(dbFileName);
        // get input value from profile
        String commentPhoto = request.getParameter("photoComment");
        System.out.println(commentPhoto);
        Picture picture = new Picture(dbFileName, commentPhoto, user);
        pictureService.savePicture(picture);
        user.addPicture(picture);
        userService.saveUser(user);

        if (fileType.equals("jpg") || fileType.equals("jpeg") || fileType.equals("png")) {
            try {
                fileUploadService.uploadFile(uploadedPicture, "\\" + fileName, year, month, day);
                System.out.println(user.getPictures());
                return "redirect:/profile";
            } catch (Exception e) {
                e.printStackTrace();
                return "profile";
            }
        } else {
            model.addAttribute("fileTypeError", "Unsupported file format (Must be 'jpg', 'jpeg' or 'png')");
            return "profile";
        }

    }
}
