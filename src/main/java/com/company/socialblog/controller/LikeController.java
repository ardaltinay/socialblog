package com.company.socialblog.controller;

import com.company.socialblog.entity.Picture;
import com.company.socialblog.entity.User;
import com.company.socialblog.service.FindUserFromSessionService;
import com.company.socialblog.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
public class LikeController {

    private FindUserFromSessionService findUserFromSessionService;
    private PictureService pictureService;

    @Autowired
    public LikeController(FindUserFromSessionService findUserFromSessionService,
                          PictureService pictureService) {
        this.findUserFromSessionService = findUserFromSessionService;
        this.pictureService = pictureService;
    }

    @PostMapping("/like")
    @ResponseBody
    public HashMap<String, Integer> likePost(HttpServletRequest request) {
        // session user alındı ve .id olarak alındı
        // get parameter dan da imageID alında
        // Picture pic = Servisadi.findbyID(imageID);
        // pic.likedUsers icerisine user eklenecek

        User user = findUserFromSessionService.findUser(request);
        HashMap<String, Integer> map = new HashMap<>();
        int picId = 0;
        Picture pic = null;
        try {
            picId = Integer.parseInt(request.getParameter("picId"));
            pic = pictureService.findPictureById(picId);
        } catch (NumberFormatException e) {
            // e.printStackTrace();
        }
        if (picId != 0 && pic != null) {
            map.put("status", 1);
            try{
                pic.addLikedUser(user);
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
