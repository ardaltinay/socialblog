package com.company.socialblog.service;

import com.company.socialblog.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Service
public class AjaxSettingsPostRequestService {
    private int response;
    private int message;

    @Autowired
    private UserService userService;

    @Autowired
    private FindUserFromSessionService findUserService;

    @Autowired
    private PasswordHashingService passwordHashingService;


    public HashMap<String, Integer> bioRequestHandler(HttpServletRequest request) {
        HashMap<String, Integer> map = new HashMap<>();

        String biography = request.getParameter("biography");
        if (biography == null) {
            response = 0;
            message = 1;
            map.put("StatusCode", response);
            map.put("ResponseMessage", message);
            return map;
        }

        // get user
        User user = findUserService.findUser(request);
        try {
            user.setBiography(biography);
            userService.saveUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            response = 0;
            message = 2;
            map.put("StatusCode", response);
            map.put("ResponseMessage", message);
            return map;
        }
        map.put("StatusCode", 1);
        return map;
    }

    public HashMap<String, Integer> emailRequestHandler(HttpServletRequest request) {
        HashMap<String, Integer> map = new HashMap<>();
        String email = request.getParameter("email");
        if (email == null) {
            response = 0;
            message = 1;
            map.put("StatusCode", response);
            map.put("ResponseMessage", message);
            return map;
        }
        // get user
        User user = findUserService.findUser(request);
        try {
            user.setEmail(email);
            userService.saveUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            response = 0;
            message = 2;
            map.put("StatusCode", response);
            map.put("ResponseMessage", message);
            return map;
        }
        map.put("StatusCode", 1);
        return map;

    }

    public HashMap<String, Integer> passwordRequestHandler(HttpServletRequest request) {
        HashMap<String, Integer> map = new HashMap<>();
        String currentPass = request.getParameter("currentPassword");
        String newPass = request.getParameter("newPassword");

        String hashedPassword = passwordHashingService.passwordHashing(currentPass);
        // get username
        String username = (String) request.getSession().getAttribute("USERNAME");
        User user = userService.findByUsernameAndPassword(username, hashedPassword);
        if (user == null) {
            response = 0;
            message = 1;
            map.put("StatusCode", response);
            map.put("ResponseMessage", message);
            return map;
        }

        if (newPass == null || newPass.length() < 8) {
            response = 0;
            message = 2;
            map.put("StatusCode", response);
            map.put("ResponseMessage", message);
            return map;
        }

        try {
            user.setPassword(passwordHashingService.passwordHashing(newPass));
            userService.saveUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            response = 0;
            message = 3;
            map.put("StatusCode", response);
            map.put("ResponseMessage", message);
            return map;
        }
        map.put("StatusCode", 1);
        return map;

    }

    public HashMap<String, Integer> deleteRequestHandler(HttpServletRequest request) {
        HashMap<String, Integer> map = new HashMap<>();
        int disableAccount;
        // get user
        User user = findUserService.findUser(request);
        try {
            user.setActive(false);
            userService.saveUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            disableAccount = 0;
            map.put("DisableAccount", disableAccount);
            return map;
        }
        request.getSession().removeAttribute("USERNAME");

        disableAccount = 1;
        map.put("DisableAccount", disableAccount);
        return map;
    }
}
