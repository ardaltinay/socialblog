package com.company.socialblog.controller;

import com.company.socialblog.entity.Picture;
import com.company.socialblog.entity.User;
import com.company.socialblog.entity.UserFollow;
import com.company.socialblog.service.FindUserFromSessionService;
import com.company.socialblog.service.UserFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
public class FollowController {
    private FindUserFromSessionService findUserFromSessionService;
    private UserFollowService userFollowService;
    @Autowired
    public FollowController(FindUserFromSessionService findUserFromSessionService,
                            UserFollowService userFollowService) {
        this.findUserFromSessionService = findUserFromSessionService;
        this.userFollowService = userFollowService;
    }

    @PostMapping("/follow")
    @ResponseBody
    public HashMap<String, Integer> followPost(HttpServletRequest request) {
        HashMap<String, Integer> map = new HashMap<>();
        int userIdTo = 0;
        int userIdFrom = findUserFromSessionService.findUser(request).getId();
        try {
            userIdTo = Integer.parseInt(request.getParameter("userIdTo"));
        } catch (NumberFormatException e) {}

        if(userIdTo != 0 && userIdFrom != 0) {
            map.put("isFollow", 1);
            try {
                UserFollow userFollow = new UserFollow();
                userFollow.setUserIdTo(userIdTo);
                userFollow.setUserIdFrom(userIdFrom);
                userFollowService.saveUserFollow(userFollow);
            } catch (Exception e) {
                map.put("isFollow", 0);
                e.printStackTrace();
            }
        } else {
            map.put("isFollow", 0);
        }
        return map;
    }
}
