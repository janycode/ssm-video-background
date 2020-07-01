package com.demo.service;

import com.demo.pojo.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User login(User user);

    int insertUser(User user);

    User validateEmail(String email);

    User queryUserByEmail(String userAccount);

    boolean updatePassword(User user);
}
