package com.company.socialblog.service;

import com.company.socialblog.dao.UserFollowRepository;
import com.company.socialblog.entity.UserFollow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserFollowServiceImpl implements UserFollowService {

    private UserFollowRepository userFollowRepository;
    @Autowired
    public UserFollowServiceImpl(UserFollowRepository userFollowRepository) {
        this.userFollowRepository = userFollowRepository;
    }

    @Override
    @Transactional
    public void saveUserFollow(UserFollow userFollow) {
        userFollowRepository.save(userFollow);
    }

    @Override
    @Transactional
    public UserFollow findByUserIdFromTo(int idFrom, int idTo) {
        return userFollowRepository.findByUserIdFromTo(idFrom, idTo);
    }

    @Override
    @Transactional
    public void delete(int userIdFrom, int userIdTo) {
        userFollowRepository.deleteUserFollowByUserIdFromAndUserIdTo(userIdFrom, userIdTo);
    }

}
