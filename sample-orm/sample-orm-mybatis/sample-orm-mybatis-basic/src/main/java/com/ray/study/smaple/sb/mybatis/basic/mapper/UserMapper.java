package com.ray.study.smaple.sb.mybatis.basic.mapper;


import com.ray.study.smaple.sb.mybatis.basic.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author shira 2019/05/09 21:27
 */
// @Mapper
public interface UserMapper {

    /**
     * 根据用户名查询用户结果集
     *
     * @param username 用户名
     * @return 查询结果
     */
    @Select("SELECT * FROM  user  WHERE username = #{username}")
    User findByUserame(@Param("username") String username);


    /**
     * 查询所有用户
     *
     * @return
     */
    @Results({@Result(property = "username", column = "username"), @Result(property = "age", column = "age")})
    @Select("SELECT username, age FROM user")
    List<User> findAll();

    /**
     * 保存用户信息
     *
     * @param user 用户信息
     * @return 成功 1 失败 0
     */
    int insert(User user);

    @Insert("INSERT INTO user(username, password, email) VALUES(#{username}, #{password}, #{email})")
    int insertBy(@Param("username") String username, @Param("password") String password, @Param("email") String email);

    @Insert("INSERT INTO user(username, password, email, age, create_time, update_time) VALUES(#{username}, #{password}, #{email}, #{age}, #{createTime}, #{updateTime} )")
    int insertByUser(User user);

    @Insert("INSERT INTO user(username, password, email, age) VALUES(#{username,jdbcType=VARCHAR}, #{password,jdbcType=VARCHAR}, #{email,jdbcType=VARCHAR}, #{age,jdbcType=INTEGER})")
    int insertByMap(Map<String, Object> map);

    @Update("UPDATE user SET email=#{email} WHERE username=#{username}")
    void update(User user);

    @Delete("DELETE FROM user WHERE id =#{id}")
    void delete(Long id);

}