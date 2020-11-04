package com.company.socialblog.controller;

import com.company.socialblog.entity.User;
import com.company.socialblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Controller
public class LoginController {

    private UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPageGet(HttpServletRequest request) {
        // Session
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

        String hashedPassword = null;
        String salt = "g9nPBBWy4uDCLzBBYKR4HnpkBAppF4jE";
        String saltedPassword = salt + password;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(saltedPassword.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            hashedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }

        // fetching users from db and control for login
        List<User> users = userService.findUsers();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(hashedPassword)) {
                // Session
                if (user.getUsername() != null) {
                    request.getSession().setAttribute("USERNAME", user.getUsername());
                }
                return "redirect:/profile";
            } else {
                model.addAttribute("message", 0);
                return "login";
            }
        }
        return "login";
    }
}
