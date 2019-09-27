package com.qf.mapper;

import com.qf.pojo.User;

import java.util.List;

public interface UserMapper {
    List<User> findAll();
    User findById(Integer id);
    void add(User user);
    void update(User user);
    void delete(Integer id);
    User findByUserNameAndPassword(String username,String password);
    User findByUserName(String username);
}
