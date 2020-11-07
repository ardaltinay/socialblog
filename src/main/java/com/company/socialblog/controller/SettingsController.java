package com.company.socialblog.controller;

import com.company.socialblog.entity.User;
import com.company.socialblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SettingsController {

    private UserService userService;

    @Autowired
    public SettingsController(UserService userService) {
        this.userService = userService;
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

        return "settings";
    }

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
}
