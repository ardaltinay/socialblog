package com.company.socialblog.controller;

import com.company.socialblog.entity.User;
import com.company.socialblog.service.PasswordHashingService;
import com.company.socialblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
public class SettingsController {

    private UserService userService;
    private PasswordHashingService passwordHashingService;

    @Autowired
    public SettingsController(UserService userService, PasswordHashingService passwordHashingService) {
        this.userService = userService;
        this.passwordHashingService = passwordHashingService;
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

        // add user biography for template to model
        model.addAttribute("userBiography", user.getBiography());
        model.addAttribute("userEmail", user.getEmail());

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

    @PostMapping("/settings/ajax")
    @ResponseBody
    public HashMap<String, Integer> settingsPagePostAjax(HttpServletRequest request, Model model) {

        int response = 1;
        int message = 0;
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
                model.addAttribute("currentUser", user);

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
                map.put("ResponseMessage", message);
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
                map.put("ResponseMessage", message);
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
                map.put("ResponseMessage", message);
                return map;

            default:
                map.put("StatusCode", 0);
                return map;
        }
    }
}
