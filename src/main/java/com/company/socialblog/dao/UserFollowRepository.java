package com.company.socialblog.dao;

import com.company.socialblog.entity.UserFollow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserFollowRepository extends JpaRepository<UserFollow, Integer> {
    List<UserFollow> findAllByUserIdFrom(int id);

    void deleteUserFollowByUserIdFromAndUserIdTo(int userIdFrom, int userIdTo);
}
