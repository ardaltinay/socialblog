package com.company.socialblog.service;

import com.company.socialblog.entity.UserFollow;

public interface UserFollowService {

    void saveUserFollow(UserFollow userFollow);

    UserFollow findByUserIdFromTo(int idFrom, int idTo);

    void delete(int userIdFrom, int userIdTo);

}
