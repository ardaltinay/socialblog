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

    public HashMap<String, Integer> getFollow(HttpServletRequest request) {
        HashMap<String, Integer> map = new HashMap<>();
        int userIdTo = 0;
        int userIdFrom = 0;
        try{
            userIdFrom = findUserFromSessionService.findUser(request).getId();
        } catch (Exception e){}

        try {
            userIdTo = Integer.parseInt(request.getParameter("userIdTo"));
        } catch (NumberFormatException e) {}

        UserFollow userFollow = userFollowService.findByUserIdFromTo(userIdFrom, userIdTo);

        if(userFollow == null) {
            map.put("isFollow", 0);
        } else {
            map.put("isFollow", 1);
        }
        return map;
    }

    public HashMap<String, Integer> setFollow(HttpServletRequest request) {
        HashMap<String, Integer> map = new HashMap<>();
        int userIdTo = 0;
        int userIdFrom = 0;
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
        HashMap<String, Integer> map = new HashMap<>();
        int userIdTo = 0;
        int userIdFrom = 0;
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