package com.company.socialblog.controller;

import com.company.socialblog.entity.User;
import com.company.socialblog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
public class SettingsController {

    private UserService userService;
    private PasswordHashingService passwordHashingService;
    private FileUploadService fileUpload;
    private UniqueFileNameService fileNameService;
    private FindUserFromSessionService findUserService;
    private FileTypeControlService fileTypeControlService;

    @Autowired
    public SettingsController(UserService userService, PasswordHashingService passwordHashingService,
                              FileUploadService fileUpload, UniqueFileNameService fileNameService,
                              FindUserFromSessionService findUserService,
                              FileTypeControlService fileTypeControlService) {
        this.userService = userService;
        this.passwordHashingService = passwordHashingService;
        this.fileUpload = fileUpload;
        this.fileNameService = fileNameService;
        this.findUserService = findUserService;
        this.fileTypeControlService = fileTypeControlService;
    }

    @GetMapping("/settings")
    public String settingsPageGet(HttpServletRequest request, Model model) {

        // session control
        User user = findUserService.findUser(request);
        if(user == null) {
            return "redirect:/login";
        }
        // add user attributes for template to model
        model.addAttribute("userBiography", user.getBiography());
        model.addAttribute("userEmail", user.getEmail());
        model.addAttribute("userTimestamp", user.getTimestamp());
        model.addAttribute("userProfilePhoto", user.getProfilePhoto());

        return "settings";
    }

    // Post method for profile photo
    @PostMapping("/settings")
    public String settingsPagePost(@RequestParam("profile-photo") MultipartFile profilePhoto,
            HttpServletRequest request, Model model) {
        // get user
        User user = findUserService.findUser(request);

        // create a file name for database table
        String fileName = fileNameService.getUniqueFileName(profilePhoto);
        String prefixFileName = fileNameService.getPrefixForDBFileName();

        // file type control
        boolean isValidFileType = fileTypeControlService.fileTypeControl(profilePhoto);
        if (isValidFileType) {
            user.setProfilePhoto(prefixFileName + fileName);
            userService.saveUser(user);
            try {
                fileUpload.uploadFile(profilePhoto, "\\" + prefixFileName + fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "redirect:/settings";
        } else {
            model.addAttribute("errorMessage", "Unsupported file format (Must be 'jpg', 'jpeg' or 'png')");
            return "settings";
        }
    }

    // Post method for settings with ajax
    @PostMapping("/settings/ajax")
    @ResponseBody
    public HashMap<String, Integer> settingsPagePostAjax(HttpServletRequest request) {

        int response = 1;
        int message;
        HashMap<String, Integer> map = new HashMap<>();

        // get user
        User user = findUserService.findUser(request);

        // get type from jquery post request
        String type = request.getParameter("type");
        // type control
        switch (type) {
            case "bio":
                // get request from html form
                String biography = request.getParameter("biography");
                if (biography == null) {
                    response = 0;
                    message = 1;
                    map.put("StatusCode", response);
                    map.put("ResponseMessage", message);
                    return map;
                }

                // set user biography and save db
                try {
                    user.setBiography(biography);
                    userService.saveUser(user);
                } catch (Exception e) {
                    e.printStackTrace();
                    response = 0;
                    message = 2;
                    map.put("StatusCode", response);
                    map.put("ResponseMessage", message);
                    return map;
                }
                map.put("StatusCode", response);
                return map;

            case "email":
                String email = request.getParameter("email");
                if (email == null) {
                    response = 0;
                    message = 1;
                    map.put("StatusCode", response);
                    map.put("ResponseMessage", message);
                    return map;
                }

                try {
                    user.setEmail(email);
                    userService.saveUser(user);
                } catch (Exception e) {
                    e.printStackTrace();
                    response = 0;
                    message = 2;
                    map.put("StatusCode", response);
                    map.put("ResponseMessage", message);
                    return map;
                }
                map.put("StatusCode", response);
                return map;

            case "pass":
                String currentPass = request.getParameter("currentPassword");
                String newPass = request.getParameter("newPassword");

                String hashedPassword = passwordHashingService.passwordHashing(currentPass);

                user = userService.findByUsernameAndPassword(user.getUsername(), hashedPassword);
                if (user == null) {
                    response = 0;
                    message = 1;
                    map.put("StatusCode", response);
                    map.put("ResponseMessage", message);
                    return map;
                }

                if (newPass == null || newPass.length() < 8) {
                    response = 0;
                    message = 2;
                    map.put("StatusCode", response);
                    map.put("ResponseMessage", message);
                    return map;
                }

                try {
                    user.setPassword(passwordHashingService.passwordHashing(newPass));
                    userService.saveUser(user);
                } catch (Exception e) {
                    e.printStackTrace();
                    response = 0;
                    message = 3;
                    map.put("StatusCode", response);
                    map.put("ResponseMessage", message);
                    return map;
                }
                map.put("StatusCode", response);
                return map;

            case "delete":
                int disableAccount = 1;

                try {
                    user.setActive(false);
                    userService.saveUser(user);
                } catch (Exception e) {
                    e.printStackTrace();
                    disableAccount = 0;
                    map.put("DisableAccount", disableAccount);
                    return map;
                }

                request.getSession().removeAttribute("USERNAME");

                map.put("DisableAccount", disableAccount);
                return map;

            default:
                map.put("StatusCode", 0);
                return map;
        }
    }
}
