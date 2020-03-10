package com.ray.study.smaple.sb.mybatis.tk.mapper;


import com.ray.study.smaple.sb.mybatis.tk.entity.User;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.BaseMapper;

/**
 * description
 *
 * @author shira 2019/05/09 21:27
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return  user
     */
    User findByUsername(String username);

}