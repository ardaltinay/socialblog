package com.company.socialblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LogoutController {

    @GetMapping("/logout")
    public String logoutPageGet(HttpServletRequest request) {
        // removing session for logout
        request.getSession().removeAttribute("USERNAME");
        return "redirect:/";
    }
}
