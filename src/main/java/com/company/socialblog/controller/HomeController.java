package com.company.socialblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

    @GetMapping("/")
    public String homePageGet(HttpServletRequest request) {
        // session
        String sessionUsername = (String) request.getSession().getAttribute("USERNAME");
        if(sessionUsername != null) {
            return "home";
        }
        return "index";
    }
}
