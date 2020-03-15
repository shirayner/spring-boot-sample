package com.ray.study.smaple.sb.mybatis.basic.mapper;

import com.ray.study.smaple.sb.mybatis.basic.entity.User;
import org.apache.ibatis.annotations.Param;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * UserMapperTest
 *
 * @author ray
 * @date 2020/3/10
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;


    @Test
    public void testUserMapper() {
        // 1.insert:  insert一条数据，并select出来验证
        String email = "tom@qq.com";
        String name = "tom";
        userMapper.insertBy(name, "password", email);
        User u = userMapper.findByUserame(name);
        assertThat(u.getEmail(), is(email));


        // 2.update:  update一条数据，并select出来验证
        String email2="tom1@qq.com";
        u.setEmail(email2);
        userMapper.update(u);
        u = userMapper.findByUserame(name);
        assertThat(u.getEmail(), is(email2));


        // 3.delete  删除这条数据，并select验证
        userMapper.delete(u.getId());
        u = userMapper.findByUserame(name);
        assertThat(u, is(nullValue()));
    }

    @Test
    public void insert() {
        // 1.insert:  insert一条数据，并select出来验证
        User user = new User();
        user.setUsername("tom");
        user.setPassword("password");
        user.setEmail("tom@qq.com");
        user.setAge(21);
        userMapper.insert(user);

        User u = userMapper.findByUserame("tom");

        assertThat(u.getAge(), is(21));
    }


    @Test
    public void insertByUser() {
        // 1.insert:  insert一条数据，并select出来验证
        User user = new User();
        user.setUsername("tom");
        user.setAge(21);
        user.setPassword("password");
        user.setEmail("tom@qq.com");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.insertByUser(user);

        User u = userMapper.findByUserame("tom");

        assertThat(u.getAge(), is(21));
    }

    @Test
    public void insertByMap() {
        // 1.insert:  insert一条数据，并select出来验证
        Map<String, Object> map = new HashMap<>();
        map.put("username", "tom");
        map.put("password", "password");
        map.put("email", "tom@qq.com");
        map.put("age", 21);
        userMapper.insertByMap(map);

        User u = userMapper.findByUserame("tom");

        assertThat(u.getAge(), is(21));
    }


}