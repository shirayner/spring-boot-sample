package com.ray.study.smaple.cache.redis.service;


import com.ray.study.smaple.cache.redis.entity.User;

/**
 * description
 *
 * @author shira 2019/05/13 18:54
 */
public interface UserService {

	User save(User user);

	void remove(String id);

	User findByUsername(User user);

}
