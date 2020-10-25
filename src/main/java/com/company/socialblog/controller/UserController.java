package com.company.socialblog.controller;

import com.company.socialblog.entity.User;
import com.company.socialblog.service.UserService;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@Controller
@Validated
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String homePageGet() {
        return "home";
    }

    @GetMapping("/register")
    public String registerPageGet(Model model) {
        model.addAttribute("message", 0);
        return "register";
    }

    @PostMapping("/register")
    public String registerPagePost(Model model, HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");


        List<User> users = userService.findUsers();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                model.addAttribute("message", 3);
                return "register";
            } else if (user.getEmail().equals(email)) {
                model.addAttribute("message", 4);
                return "register";
            }
        }

        User theUser = new User(username, password, email);
        try {
            userService.saveUser(theUser);
        } catch (Exception e) {
            model.addAttribute("message", 2);
            return "register";
        }

        // Session
        if (theUser.getUsername() != null) {
            request.getSession().setAttribute("USERNAME", theUser.getUsername());
        }

        model.addAttribute("message", 1);
        return "register";
    }

    @GetMapping("/profile")
    public String profilePageGet(Model model, HttpServletRequest request) {
        // Session
        String sessionUsername = (String) request.getSession().getAttribute("USERNAME");
        if (sessionUsername == null) {
            return "redirect:/register";
        }
        model.addAttribute("USERNAME", sessionUsername);
        return "profile";
    }
}
