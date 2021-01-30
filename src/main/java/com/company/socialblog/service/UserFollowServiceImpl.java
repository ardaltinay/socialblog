package com.company.socialblog.service;

import com.company.socialblog.dao.UserFollowRepository;
import com.company.socialblog.entity.UserFollow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserFollowServiceImpl implements UserFollowService {

    private UserFollowRepository userFollowRepository;
    @Autowired
    public UserFollowServiceImpl(UserFollowRepository userFollowRepository) {
        this.userFollowRepository = userFollowRepository;
    }

    @Override
    public void saveUserFollow(UserFollow userFollow) {
        userFollowRepository.save(userFollow);
    }


}
