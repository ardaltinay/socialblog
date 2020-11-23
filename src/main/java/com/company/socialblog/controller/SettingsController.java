package com.company.socialblog.controller;

import com.company.socialblog.entity.User;
import com.company.socialblog.service.FileUploadService;
import com.company.socialblog.service.PasswordHashingService;
import com.company.socialblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@Controller
public class SettingsController {

    private UserService userService;
    private PasswordHashingService passwordHashingService;
    private FileUploadService fileUpload;

    @Autowired
    public SettingsController(UserService userService, PasswordHashingService passwordHashingService, FileUploadService fileUpload) {
        this.userService = userService;
        this.passwordHashingService = passwordHashingService;
        this.fileUpload = fileUpload;
    }

    @GetMapping("/settings")
    public String settingsPageGet(HttpServletRequest request, Model model) {
        // session control
        String sessionUsername = (String) request.getSession().getAttribute("USERNAME");
        if (sessionUsername == null) {
            return "redirect:/login";
        }
        // finding user by username
        User user = userService.findByUsername(sessionUsername);

        // add user attributes for template to model
        model.addAttribute("userBiography", user.getBiography());
        model.addAttribute("userEmail", user.getEmail());
        model.addAttribute("userTimestamp", user.getTimestamp());

        return "settings";
    }
    /*
    @PostMapping("/settings")
    public String settingsPagePost(HttpServletRequest request, Model model) {
        // get request from html form
        String biography = request.getParameter("biography");

        // session
        String sessionUsername = (String) request.getSession().getAttribute("USERNAME");

        // finding user by username
        User user = userService.findByUsername(sessionUsername);

        // set user biography and save db
        user.setBiography(biography);
        userService.saveUser(user);

        // add user biography for template to model
        model.addAttribute("userBiography", user.getBiography());

        return "settings";
    }
     */

    // Post method for profile photo
    @PostMapping("/settings")
    public String settingsPagePost(@RequestParam("profilephoto") MultipartFile profilePhoto,
                                   HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        // Create a new unique file name for each file
        String fileName = profilePhoto.getOriginalFilename();
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmssSSSS").format(new Date());
        String newFileName = timeStamp + "." + fileType;

        // Checking file type control
        String[] fileTypes = {"jpeg", "jpg", "png"};
        for (String types : fileTypes) {
            if (fileType.equals(types)) {
                // Finding user by username
                String sessionUsername = (String) request.getSession().getAttribute("USERNAME");
                User user = userService.findByUsername(sessionUsername);

                // Upload file, set profile photo and save database
                try {
                    fileUpload.uploadFile(profilePhoto, newFileName);
                    user.setProfilePhoto(newFileName);
                    userService.saveUser(user);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "File type must be 'jpeg', 'jpg' or 'png'");
            }
        }

        return "redirect:/settings";
    }

    // Post method for settings with ajax
    @PostMapping("/settings/ajax")
    @ResponseBody
    public HashMap<String, Integer> settingsPagePostAjax(HttpServletRequest request) {

        int response = 1;
        int message;
        HashMap<String, Integer> map = new HashMap<>();

        // session
        String sessionUsername = (String) request.getSession().getAttribute("USERNAME");
        if (sessionUsername == null) {
            response = 0;
        }

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

                // finding user by username
                User user = userService.findByUsername(sessionUsername);

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

                user = userService.findByUsername(sessionUsername);

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

                user = userService.findByUsernameAndPassword(sessionUsername, hashedPassword);
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

                user = userService.findByUsername(sessionUsername);

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
