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
    private FileUploadService fileUpload;
    private UniqueFileNameService fileNameService;
    private FindUserFromSessionService findUserService;
    private FileTypeControlService fileTypeControlService;
    private AjaxSettingsPostRequestService ajaxRequestService;

    @Autowired
    public SettingsController(UserService userService,
                              FileUploadService fileUpload,
                              UniqueFileNameService fileNameService,
                              FindUserFromSessionService findUserService,
                              FileTypeControlService fileTypeControlService,
                              AjaxSettingsPostRequestService ajaxRequestService) {
        this.userService = userService;
        this.fileUpload = fileUpload;
        this.fileNameService = fileNameService;
        this.findUserService = findUserService;
        this.fileTypeControlService = fileTypeControlService;
        this.ajaxRequestService = ajaxRequestService;
    }

    @GetMapping("/settings")
    public String settingsPageGet(HttpServletRequest request, Model model) {

        // session control
        User user = findUserService.findUser(request);
        if(user == null) {
            return "redirect:/login";
        }
        // add user attributes for template to model
        model.addAttribute("user", user);

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
                fileUpload.uploadFile(profilePhoto, "\\" + fileName);
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
        HashMap<String, Integer> map = new HashMap<>();

        // get type from jquery post request
        String type = request.getParameter("type");
        // type control
        switch (type) {
            case "bio":
                return ajaxRequestService.bioRequestHandler(request);

            case "email":
                return ajaxRequestService.emailRequestHandler(request);

            case "pass":
                return ajaxRequestService.passwordRequestHandler(request);

            case "delete":
                return ajaxRequestService.deleteRequestHandler(request);

            default:
                map.put("StatusCode", 0);
                return map;
        }
    }
}
