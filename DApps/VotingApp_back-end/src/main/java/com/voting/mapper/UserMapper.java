package com.voting.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.voting.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper extends BaseMapper<User> {

    @Select("select count(*) from (select * from `user` where community_id = #{communityId} GROUP BY room) as temp")
    Integer queryRoomCount(@Param("communityId")String communityId);
}
