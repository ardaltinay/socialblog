package com.company.socialblog.service;

import com.company.socialblog.entity.Picture;
import com.company.socialblog.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Service
public class AjaxLikePostRequestService {

    private FindUserFromSessionService findUserFromSessionService;
    private PictureService pictureService;

    @Autowired
    public AjaxLikePostRequestService(FindUserFromSessionService findUserFromSessionService,
                          PictureService pictureService) {
        this.findUserFromSessionService = findUserFromSessionService;
        this.pictureService = pictureService;
    }

    public HashMap<String, Integer> getLike(HttpServletRequest request) {
        User user = findUserFromSessionService.findUser(request);
        HashMap<String, Integer> map = new HashMap<>();
        int picId = 0;
        Picture pic = null;
        try {
            picId = Integer.parseInt(request.getParameter("picId"));
            pic = pictureService.findPictureById(picId);
        } catch (NumberFormatException e) {}
        if (picId != 0 && pic != null) {
            Boolean userExist = false;
            try{
                userExist = pic.getLikedUser(user);
            } catch (Exception e) { }
            if (userExist) {
                map.put("isLiked", 1);
            } else {
                map.put("isLiked", 0);
            }
        } else {
            map.put("isLiked", 0);
        }
        return map;
    }

    public HashMap<String, Integer> setLike(HttpServletRequest request) {
        User user = findUserFromSessionService.findUser(request);
        HashMap<String, Integer> map = new HashMap<>();
        int picId = 0;
        Picture pic = null;
        try {
            picId = Integer.parseInt(request.getParameter("picId"));
            pic = pictureService.findPictureById(picId);
        } catch (NumberFormatException e) {}
        if (picId != 0 && pic != null) {
            map.put("status", 1);
            try{
                pic.addLikedUser(user);
                // pic.setLikedUsers(); // denenecek calisma prensibi olarak
                pictureService.savePicture(pic);
            } catch (Exception e) {
                map.put("status", 2);
            }
        } else {
            map.put("status", 0);
        }
        return map;
    }

}
