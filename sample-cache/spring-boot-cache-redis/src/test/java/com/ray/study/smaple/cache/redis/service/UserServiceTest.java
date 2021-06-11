package com.ray.study.smaple.cache.redis.service;

import com.ray.study.smaple.cache.redis.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * UserServiceTest
 *
 * @author ray
 * @date 2020/3/25
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@Rollback
@Slf4j
class UserServiceTest {

    @Autowired
    UserService userService;


    @Test
    public void testCache() {
        User user0 = new User("aatomcat000", "password", "aatomcat000@qq.com", 20);
        List<User> userToAddList = new ArrayList<>();
        userToAddList.add(user0);
        userToAddList.add(new User("tomcat", "password", "tomcat@qq.com", 21));
        userToAddList.add(new User("tttomcat", "password", "tttomcat@qq.com", 22));
        userToAddList.add(new User("tttomcat111", "password", "tttomcat111@qq.com", 23));
        userToAddList.add(new User("tomcataaa", "password", "tomcataaa@qq.com", 24));

        // 新增用户
        userToAddList =  userToAddList.stream().map(user -> userService.save(user)).collect(Collectors.toList());
        log.info("==========新增用户完毕============");

        User user1 = userService.findByUsername(user0);
        User user2 = userService.findByUsername(user0);
        User user3 = userService.findByUsername(user0);

        userToAddList.forEach(user -> userService.remove(user.getUsername()));
    }
}