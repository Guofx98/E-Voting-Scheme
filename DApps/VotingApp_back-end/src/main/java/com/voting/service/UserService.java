package com.voting.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.voting.entity.User;

public interface UserService extends IService<User> {
    Integer queryRoomCount(String communityId);
}
