package com.company.socialblog.dao;

import com.company.socialblog.entity.UserFollow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserFollowRepository extends JpaRepository<UserFollow, Integer> {
    List<UserFollow> findAllByUserIdFrom(int id);

    //@Query("delete from UserFollow where userIdFrom = :userIdFrom and userIdTo = :userIdTo")
    //void deleteUserFollow(@Param("userIdFrom")int userIdFrom, @Param("userIdTo")int userIdTo);

    //UserFollow findUserFollowByUserIdFromAndUserIdTo(int userIdFrom, int userIdTo);

    void deleteUserFollowByUserIdFromAndUserIdTo(int userIdFrom, int userIdTo);
}
