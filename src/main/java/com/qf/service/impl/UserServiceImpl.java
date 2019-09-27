package com.qf.service.impl;

import com.qf.mapper.UserMapper;
import com.qf.pojo.User;
import com.qf.service.UserService;
import com.qf.utils.EmailUtils;
import com.qf.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    @Transactional
    public void regist(User user) {
        userMapper.add(user);
        EmailUtils.sendEmail(user);
    }

    @Override
    public User chackUser(String username) {
        User byUserName = userMapper.findByUserName(username);
        return byUserName;
    }

    @Override
    public User chackLogin(String username, String password) {
        String s = MD5Utils.StrongPwd(password);
        User byUserNameAndPassword = userMapper.findByUserNameAndPassword(username, s);
        return byUserNameAndPassword;
    }

}
