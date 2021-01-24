package com.company.socialblog.controller;

import com.company.socialblog.entity.Picture;
import com.company.socialblog.entity.User;
import com.company.socialblog.service.FindUserFromSessionService;
import com.company.socialblog.service.PictureService;
import com.company.socialblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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
    public void likePost(HttpServletRequest request) {
        // session user alındı ve .id olarak alındı
        // get parameter dan da imageID alında
        // Picture pic = Servisadi.findbyID(imageID);
        // pic.likedUsers icerisine user eklenecek

        User user = findUserFromSessionService.findUser(request);

        try {
            int picId = Integer.parseInt(request.getParameter("picId"));
            Picture pic = pictureService.findPictureById(picId);
            System.out.println(pic);
            pic.addLikedUser(user);
            pictureService.savePicture(pic);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }
}