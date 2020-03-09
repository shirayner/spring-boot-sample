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
@Mapper
public interface UserMapper {

	/**
	 * 根据用户名查询用户结果集
	 *
	 * @param name 用户名
	 * @return 查询结果
	 */
	@Select("SELECT * FROM  user  WHERE name = #{name}")
	User findByName(@Param("name") String name);


	/**
	 * 查询所有用户
	 * @return
	 */
	@Results({
			@Result(property = "name", column = "name"),
			@Result(property = "age", column = "age")
	})
	@Select("SELECT name, age FROM user")
	List<User> findAll();

	/**
	 * 保存用户信息
	 *
	 * @param user 用户信息
	 * @return 成功 1 失败 0
	 */
	int insert(User user);

	@Insert("INSERT INTO USER(NAME, AGE) VALUES(#{name}, #{age})")
	int insertByNameAndAge(@Param("name") String name, @Param("age") Integer age);

	@Insert("INSERT INTO USER(NAME, AGE, CREATION_DATE, LAST_UPDATE_DATE) VALUES(#{name}, #{age}, #{creationDate}, #{lastUpdateDate} )")
	int insertByUser(User user);

	@Insert("INSERT INTO USER(NAME, AGE) VALUES(#{name,jdbcType=VARCHAR}, #{age,jdbcType=INTEGER})")
	int insertByMap(Map<String, Object> map);


	@Update("UPDATE user SET age=#{age} WHERE name=#{name}")
	void update(User user);

	@Delete("DELETE FROM user WHERE id =#{id}")
	void delete(Long id);

}