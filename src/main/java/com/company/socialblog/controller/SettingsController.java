package com.company.socialblog.controller;

import com.company.socialblog.entity.User;
import com.company.socialblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public String settingsPageGet(HttpServletRequest request) {
        // session control
        String sessionUsername = (String) request.getSession().getAttribute("USERNAME");
        if (sessionUsername == null) {
            return "redirect:/login";
        }

        return "settings";
    }

    @PostMapping("/settings")
    public String settingsPagePost(HttpServletRequest request, Model model) {
        String biography = request.getParameter("biography");
        String sessionUsername = (String) request.getSession().getAttribute("USERNAME");
        User user = userService.findByUsername(sessionUsername);
        System.out.println(user);
        user.setBiography(biography);
        System.out.println(user.getBiography());
        System.out.println(user);
        model.addAttribute("userBiography", user.getBiography());


        return "profile";

    }
}
