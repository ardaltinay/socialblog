package com.company.socialblog.service;

import com.company.socialblog.dao.UserFollowRepository;
import com.company.socialblog.entity.UserFollow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

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
    public List<UserFollow> findByUserIdFrom(int id) {
        return userFollowRepository.findAllByUserIdFrom(id);
    }

    @Override
    @Transactional
    public void delete(int userIdFrom, int userIdTo) {
        userFollowRepository.deleteUserFollowByUserIdFromAndUserIdTo(userIdFrom, userIdTo);
    }

    /*@Override
    public UserFollow findUserFollowByUserIdFromAndUserIdTo(int userIdFrom, int userIdTo) {
        return userFollowRepository.findUserFollowByUserIdFromAndUserIdTo(userIdFrom, userIdTo);
    }*/

}
