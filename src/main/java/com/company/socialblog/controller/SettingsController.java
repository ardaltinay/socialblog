package com.company.socialblog.controller;

import com.company.socialblog.entity.User;
import com.company.socialblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.HashMap;

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
        model.addAttribute("userEmail", user.getEmail());

        return "settings";
    }
    /*
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
     */

    @PostMapping("/settings/ajax")
    @ResponseBody
    public HashMap<String, Integer> settingsPagePostAjax(HttpServletRequest request) {

        int response = 1;
        // session
        String sessionUsername = (String) request.getSession().getAttribute("USERNAME");
        if (sessionUsername == null) {
            response = 0;
        }

        // get type from jquery post request
        String type = request.getParameter("type");
        // type control
        if (type.equals("bio")) {

            // get request from html form
            String biography = request.getParameter("biography");
            if (biography == null) {
                response = 0;
            }

            // finding user by username
            User user = userService.findByUsername(sessionUsername);

            // set user biography and save db
            try {
                user.setBiography(biography);
                userService.saveUser(user);
            } catch(Exception e) {
                e.printStackTrace();
                response = 0;
            }

            HashMap<String, Integer> map = new HashMap<>();
            map.put("StatusCode", response);

            return map;

        } else if (type.equals("email")) {

            String email = request.getParameter("email");
            if (email == null) {
                response = 0;
            }

            User user = userService.findByUsername(sessionUsername);

            try {
                user.setEmail(email);
                userService.saveUser(user);
            } catch(Exception e) {
                e.printStackTrace();
                response = 0;
            }

            HashMap<String, Integer> map = new HashMap<>();
            map.put("StatusCode", response);

            return map;

        } else {
            HashMap<String, Integer> map = new HashMap<>();
            map.put("StatusCode", 0);
            return map;
        }

    }

}
