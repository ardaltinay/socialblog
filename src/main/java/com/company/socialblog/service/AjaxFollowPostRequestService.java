package com.company.socialblog.service;

import com.company.socialblog.entity.User;
import com.company.socialblog.entity.UserFollow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Service
public class AjaxFollowPostRequestService {
    private FindUserFromSessionService findUserFromSessionService;
    private UserFollowService userFollowService;

    @Autowired
    public AjaxFollowPostRequestService(FindUserFromSessionService findUserFromSessionService,
                                      UserFollowService userFollowService) {
        this.findUserFromSessionService = findUserFromSessionService;
        this.userFollowService = userFollowService;
    }

    HashMap<String, Integer> map = new HashMap<>();
    int userIdTo = 0;
    int userIdFrom = 0;

    public HashMap<String, Integer> getFollow(HttpServletRequest request) {
        try{
            userIdFrom = findUserFromSessionService.findUser(request).getId();
        } catch (Exception e){}

        try {
            userIdTo = Integer.parseInt(request.getParameter("userIdTo"));
        } catch (NumberFormatException e) {}

        List<UserFollow> userFollows = userFollowService.findByUserIdFrom(userIdFrom);

        if(userFollows.size() == 0) {
            map.put("isFollow", 0);
        } else {
            for (UserFollow userFollow : userFollows) {
                if(userIdTo == userFollow.getUserIdTo()) {
                    map.put("isFollow", 1);
                } else {
                    map.put("isFollow", 0);
                }
            }
        }
        return map;
    }

    public HashMap<String, Integer> setFollow(HttpServletRequest request) {
        try{
            userIdFrom = findUserFromSessionService.findUser(request).getId();
        } catch (Exception e){}

        try {
            userIdTo = Integer.parseInt(request.getParameter("userIdTo"));
        } catch (NumberFormatException e) {}

        if (userIdTo != 0 && userIdFrom != 0) {
            map.put("status", 1);
            try {
                UserFollow userFollow = new UserFollow();
                userFollow.setUserIdTo(userIdTo);
                userFollow.setUserIdFrom(userIdFrom);
                userFollowService.saveUserFollow(userFollow);
            } catch (Exception e) {
                map.put("status", 0);
                e.printStackTrace();
            }
        } else {
            map.put("status", 0);
        }
        return map;

    }

    public HashMap<String, Integer> delFollow(HttpServletRequest request) {
        try{
            userIdFrom = findUserFromSessionService.findUser(request).getId();
        } catch (Exception e){}

        try {
            userIdTo = Integer.parseInt(request.getParameter("userIdTo"));
        } catch (NumberFormatException e) {}

        try{
            userFollowService.delete(userIdFrom, userIdTo);
            map.put("status", 1);
        } catch (Exception e) {
            map.put("status", 0);
       }
        return map;
    }
}