package com.company.socialblog.controller;

import com.company.socialblog.entity.User;
import com.company.socialblog.service.PasswordHashingService;
import com.company.socialblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;;
import java.util.List;

@Controller
public class LoginController {

    private UserService userService;
    private PasswordHashingService passwordHashingService;

    @Autowired
    public LoginController(UserService userService, PasswordHashingService passwordHashingService) {
        this.userService = userService;
        this.passwordHashingService = passwordHashingService;
    }

    @GetMapping("/login")
    public String loginPageGet(HttpServletRequest request) {
        // Session control
        String sessionUsername = (String) request.getSession().getAttribute("USERNAME");
        if (sessionUsername != null) {
            return "redirect:/profile";
        } else {
            return "login";
        }
    }

    @PostMapping("/login")
    public String loginPagePost(Model model, HttpServletRequest request) {
        // get value from login form
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // hashing password from passwordHashingService
        String hashedPassword = passwordHashingService.passwordHashing(password);

        // fetching users from db and control for login
        List<User> users = userService.findUsers();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(hashedPassword) && user.isActive()) {
                // Session
                if (user.getUsername() != null) {
                    request.getSession().setAttribute("USERNAME", user.getUsername());
                }
                return "redirect:/profile";
            }
        }
        model.addAttribute("message", 0);
        return "login";
    }
}
