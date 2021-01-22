package com.company.socialblog.controller;

import com.company.socialblog.entity.User;
import com.company.socialblog.exception.UserNotFoundException;
import com.company.socialblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{username}")
    public String userPageGet(@PathVariable("username") String username, Model model, HttpServletRequest request) {
        // finding user from db
        User user = userService.findByUsername(username);
        if(user == null) {
            throw new UserNotFoundException("Username: " + username + " not found.");
        }
        model.addAttribute("username", user.getUsername());
        model.addAttribute("userProfilePhoto", user.getProfilePhoto());
        model.addAttribute("userBiography", user.getBiography());
        model.addAttribute("userEmail", user.getEmail());
        model.addAttribute("pictures", user.getPictures());

        return "user-profile";

    }
}
