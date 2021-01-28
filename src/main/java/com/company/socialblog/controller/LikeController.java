package com.company.socialblog.controller;

import com.company.socialblog.entity.Picture;
import com.company.socialblog.entity.User;
import com.company.socialblog.service.AjaxLikePostRequestService;
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

    private AjaxLikePostRequestService ajaxLikePostRequestService;

    @Autowired
    public LikeController(AjaxLikePostRequestService ajaxLikePostRequestService) {
        this.ajaxLikePostRequestService = ajaxLikePostRequestService;
    }

    @PostMapping("/like")
    @ResponseBody
    public HashMap<String, Integer> likePost(HttpServletRequest request) {

        String type = request.getParameter("type");
        switch (type) {
            case "get":
                return ajaxLikePostRequestService.getLike(request);
            case "set":
                return ajaxLikePostRequestService.setLike(request);
            case "del":
                return ajaxLikePostRequestService.delLike(request);
            default:
                return null;
        }

    }
}
