package com.ray.study.smaple.cache.redis.service.impl;


import com.ray.study.smaple.cache.redis.entity.User;
import com.ray.study.smaple.cache.redis.repository.UserRepository;
import com.ray.study.smaple.cache.redis.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * description
 *
 * @author shira 2019/05/13 18:58
 */
@Slf4j
@Service
@CacheConfig(cacheNames = "user")
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @CachePut(key = "#user.username")
    public User save(User user) {
        User u = userRepository.save(user);
        log.info("新增：缓存用户，用户id为:{}", u.getId());
        return u;
    }


    @Override
    @CacheEvict(key = "#p0")
    public void remove(String username) {
        log.info("删除：删除缓存，用户username为:{}", username);
        userRepository.deleteByUsername(username);
    }

    @Override
    @Cacheable(key = "#user.username")
    public User findByUsername(User user) {
        user = userRepository.findByUsername(user.getUsername());
        log.info("查询：缓存用户，用户id为:{}", user.getId());
        return user;
    }

}
