package com.company.socialblog.service;

import com.company.socialblog.entity.User;

import java.util.List;

public interface UserService {

    void saveUser(User user);

    List<User> findUsers();

    User findByUsername(String username);

    User findByUsernameAndPassword(String username, String password);

}
