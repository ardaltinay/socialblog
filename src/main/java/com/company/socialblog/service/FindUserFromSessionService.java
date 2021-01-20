package com.company.socialblog.service;

import com.company.socialblog.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class FindUserFromSessionService {

    @Autowired
    private UserService userService;

    public User findUser(HttpServletRequest request) {
        String sessionUsername = (String) request.getSession().getAttribute("USERNAME");
        if (sessionUsername == null) {
            return null;
        } else {
            User user = userService.findByUsername(sessionUsername);
            return user;
        }
    }
}
