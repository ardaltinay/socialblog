package com.company.socialblog.controller;

import com.company.socialblog.entity.User;
import com.company.socialblog.service.FindUserFromSessionService;
import com.company.socialblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class SearchController {
    private UserService userService;
    private FindUserFromSessionService findUserFromSessionService;
    @Autowired
    public SearchController(UserService userService,
                            FindUserFromSessionService findUserFromSessionService) {
        this.userService = userService;
        this.findUserFromSessionService = findUserFromSessionService;
    }

    @GetMapping("/search")
    public String searchPageGet(HttpServletRequest request, Model model) {
        User sessionUser = findUserFromSessionService.findUser(request);
        if (sessionUser == null) {
            return "redirect:/login";
        }
        String searchParam = request.getParameter("user");
        List<User> users = userService.findByUsernameWithParam(searchParam);
        model.addAttribute("users", users);
        return "search-result";
    }
}
