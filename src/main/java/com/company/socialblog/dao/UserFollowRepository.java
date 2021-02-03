package com.company.socialblog.dao;

import com.company.socialblog.entity.UserFollow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserFollowRepository extends JpaRepository<UserFollow, Integer> {
    // List<UserFollow> findAllByUserIdFrom(int id);

    @Query("select u from UserFollow u where u.userIdFrom = :userIdFrom and u.userIdTo = :userIdTo")
    UserFollow findByUserIdFromTo(@Param("userIdFrom") int userIdFrom, @Param("userIdTo") int userIdTo);

    void deleteUserFollowByUserIdFromAndUserIdTo(int userIdFrom, int userIdTo);
}
