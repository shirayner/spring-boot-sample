package com.ray.study.smaple.cache.service;


import com.ray.study.smaple.cache.entity.User;

/**
 * description
 *
 * @author shira 2019/05/13 18:54
 */
public interface UserService {

	User save(User user);

	void remove(Long id);

	User findByUsername(User user);

}
