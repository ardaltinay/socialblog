package com.company.socialblog.controller;

import com.company.socialblog.entity.User;
import com.company.socialblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // add an databinder... to convert trim input strings
    // remove leading and trailing whitespace
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/")
    public String homePageGet() {
        return "home";
    }

    @GetMapping("/register")
    public String registerPageGet(Model model, @ModelAttribute("theUser") User theUser) {
        model.addAttribute("message", 0);
        return "register";
    }

    // postmapping a model ekleyince neden getmapping a da eklemek gerekiyor?
    @PostMapping("/register")
    public String registerPagePost(Model model, HttpServletRequest request,
                        @Valid @ModelAttribute("theUser") User theUser, BindingResult result) {

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

        if(result.hasErrors()) {
            return "register";
        }

        theUser = new User(username, password, email);

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

    @GetMapping("/login")
    public String loginPageGet() {
        return "login";
    }

    @PostMapping("/login")
    public String loginPagePost(Model model, HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        List<User> users = userService.findUsers();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                // Session
                if (user.getUsername() != null) {
                    request.getSession().setAttribute("USERNAME", user.getUsername());
                }
                return "redirect:/profile";
            } else {
                model.addAttribute("message", 0);
            }
        }
        return "login";
    }
}
