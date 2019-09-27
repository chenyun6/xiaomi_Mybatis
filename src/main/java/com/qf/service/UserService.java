package com.qf.service;

import com.qf.pojo.User;

public interface UserService {
    void regist(User user);
    User chackUser(String username);
    User chackLogin(String username, String password);
}
