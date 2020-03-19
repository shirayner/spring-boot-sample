package com.ray.study.smaple.cache.service;


import com.ray.study.smaple.cache.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * UserServiceTest
 *
 * @author ray
 * @date 2020/3/19
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback
@Slf4j
class UserServiceTest {

    @Autowired
    UserService userService;


    @Test
    public void testCache() {
        User user0 = new User("aatomcat000","password", "aatomcat000@qq.com", 20);
        List<User> userToAddList = new ArrayList<>();
        userToAddList.add(user0);
        userToAddList.add(new User("tomcat","password", "tomcat@qq.com",21));
        userToAddList.add(new User("tttomcat","password", "tttomcat@qq.com",22));
        userToAddList.add(new User("tttomcat111","password", "tttomcat111@qq.com",23));
        userToAddList.add(new User("tomcataaa","password", "tomcataaa@qq.com",24));

        // 新增用户
        userToAddList.forEach(user -> {
            userService.save(user);
        });
        log.info("==========新增用户完毕============");

        User user1 = userService.findByUsername(user0);
        User user2 = userService.findByUsername(user0);
        User user3 = userService.findByUsername(user0);

        userService.remove(user0.getId());
    }
}