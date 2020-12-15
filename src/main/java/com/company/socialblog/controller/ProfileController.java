package com.company.socialblog.controller;

import com.company.socialblog.entity.User;
import com.company.socialblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    private UserService userService;

    @Autowired
    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String profilePageGet(Model model, HttpServletRequest request) {
        // Session
        String sessionUsername = (String) request.getSession().getAttribute("USERNAME");
        if (sessionUsername == null) {
            return "redirect:/login";
        }
        // get user from session
        User user = userService.findByUsername(sessionUsername);

        // add user attributes for template to model
        model.addAttribute("USERNAME", sessionUsername);
        model.addAttribute("userProfilePhoto", user.getProfilePhoto());
        model.addAttribute("userBiography", user.getBiography());

        return "profile";
    }
}
