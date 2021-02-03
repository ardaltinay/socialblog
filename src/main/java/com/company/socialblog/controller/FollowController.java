package com.company.socialblog.controller;

import com.company.socialblog.service.AjaxFollowPostRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
public class FollowController {
    private AjaxFollowPostRequestService ajaxFollowPostRequestService;
    @Autowired
    public FollowController(AjaxFollowPostRequestService ajaxFollowPostRequestService) {
        this.ajaxFollowPostRequestService = ajaxFollowPostRequestService;
    }

    @PostMapping("/follow")
    @ResponseBody
    public HashMap<String, Integer> followPost(HttpServletRequest request) {
        HashMap<String, Integer> map = new HashMap<>();
        String type = request.getParameter("type");
        switch (type) {
            case "get":
                return ajaxFollowPostRequestService.getFollow(request);
            case "set":
                return ajaxFollowPostRequestService.setFollow(request);
            case "del":
                return ajaxFollowPostRequestService.delFollow(request);
            default:
                map.put("type", 0);
                return map;
        }
    }
}
