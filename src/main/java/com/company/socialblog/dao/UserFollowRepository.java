package com.company.socialblog.dao;

import com.company.socialblog.entity.UserFollow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFollowRepository extends JpaRepository<UserFollow, Integer> {
}
