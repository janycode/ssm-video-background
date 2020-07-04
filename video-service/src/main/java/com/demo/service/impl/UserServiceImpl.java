package com.demo.service.impl;

import com.demo.dao.UserMapper;
import com.demo.pojo.User;
import com.demo.pojo.UserExample;
import com.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> findAll() {
        return userMapper.selectByExample(null);
    }

    @Override
    public User login(User user) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andEmailEqualTo(user.getEmail());
        criteria.andPasswordEqualTo(user.getPassword());
        List<User> userList = userMapper.selectByExample(userExample);
        return userList.size() > 0 ? userList.get(0) : null;
    }

    @Override
    public int insertUser(User user) {
        return userMapper.insert(user);
    }

    @Override
    public User validateEmail(String email) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andEmailEqualTo(email);
        List<User> userList = userMapper.selectByExample(userExample);
        return userList.size() > 0 ? userList.get(0) : null;
    }

    @Override
    public User queryUserByEmail(String userAccount) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andEmailEqualTo(userAccount);
        List<User> userList = userMapper.selectByExample(userExample);
        return userList.size() > 0 ? userList.get(0) : null;
    }

    @Override
    public boolean updatePassword(User user) {
        return userMapper.updateByPrimaryKey(user) == 1;
    }

    @Override
    public int updateUserByEmail(User user) {
        return userMapper.updateByEmail(user);
    }
}
