package com.company.socialblog.controller;

import com.company.socialblog.entity.User;
import com.company.socialblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Controller
public class RegisterController {

    private UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    // add a data binder... to convert trim input strings
    // remove leading and trailing whitespace
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/register")
    public String registerPageGet(Model model, HttpServletRequest request,
                                  @ModelAttribute("theUser") User theUser) {
        // Session
        String sessionUsername = (String) request.getSession().getAttribute("USERNAME");
        if (sessionUsername != null) {
            return "redirect:/profile";
        } else {
            return "register";
        }
    }

    // postmapping a model ekleyince neden getmapping a da eklemek gerekiyor?
    @PostMapping("/register")
    public String registerPagePost(Model model, HttpServletRequest request,
                                   @Valid @ModelAttribute("theUser") User theUser, BindingResult result) {
        // get value from register form
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

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
            if (user.getUsername().equals(username)) {
                model.addAttribute("message", 3);
                return "register";
            } else if (user.getEmail().equals(email)){
                model.addAttribute("message", 4);
                return "register";
            }
        }

        // if validation not ok
        if(result.hasErrors()) {
            return "register";
        }

        // save user to db if validation ok
        theUser = new User(username, hashedPassword, email);
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
        return "profile";
    }
}
