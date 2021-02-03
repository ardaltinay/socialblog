package com.company.socialblog.service;

import com.company.socialblog.entity.UserFollow;

import java.util.List;

public interface UserFollowService {

    void saveUserFollow(UserFollow userFollow);

    List<UserFollow> findByUserIdFrom(int id);

    void delete(int userIdFrom, int userIdTo);

}
