package com.example.hotmall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.example.hotmall.model.User;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Update("update user set type = type +1 where id = ${userId}")
    public void updateType(Integer userId);
}
