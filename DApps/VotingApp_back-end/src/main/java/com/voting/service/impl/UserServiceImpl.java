package com.voting.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.voting.entity.User;
import com.voting.mapper.UserMapper;
import com.voting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public Integer queryRoomCount(String communityId) {
        return userMapper.queryRoomCount(communityId);
    }
}
