package com.company.socialblog.controller;

import com.company.socialblog.entity.User;
import com.company.socialblog.exception.UserNotFoundException;
import com.company.socialblog.service.FindUserFromSessionService;
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
    private FindUserFromSessionService findUserFromSessionService;

    @Autowired
    public UserController(UserService userService,
                          FindUserFromSessionService findUserFromSessionService) {
        this.userService = userService;
        this.findUserFromSessionService = findUserFromSessionService;
    }

    @GetMapping("/user/{username}")
    public String userPageGet(@PathVariable("username") String username, Model model,HttpServletRequest request) {
        User sessionUser = findUserFromSessionService.findUser(request);
        if(sessionUser == null) {
            return "redirect:/login?path=/user/" + username;
        }
        // finding user from db
        User user = userService.findByUsername(username);
        if(user == null) {
            throw new UserNotFoundException("Username: " + username + " not found.");
        }
        model.addAttribute("user", user);

        return "user-profile";

    }
}
