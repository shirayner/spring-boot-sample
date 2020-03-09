package com.ray.study.smaple.sb.mybatis.basic.mapper;

import com.ray.study.smaple.sb.mybatis.basic.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

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
        userMapper.insertByNameAndAge("tom", 21);
        User u = userMapper.findByName("tom");
        assertThat(u.getAge(), is(21));


        // 2.update:  update一条数据，并select出来验证
        u.setAge(30);
        userMapper.update(u);
        u = userMapper.findByName("tom");
        assertThat(u.getAge(), is(30));


        // 3.delete  删除这条数据，并select验证
        userMapper.delete(u.getId());
        u = userMapper.findByName("tom");
        assertThat(u, is(nullValue()));
    }

    @Test
    public void insert() {
        // 1.insert:  insert一条数据，并select出来验证
        User user = new User();
        user.setName("tom");
        user.setAge(21);
        userMapper.insert(user);

        User u = userMapper.findByName("tom");

        assertThat(u.getAge(), is(21));
    }


    @Test
    public void insertByUser() {
        // 1.insert:  insert一条数据，并select出来验证
        User user = new User();
        user.setName("tom");
        user.setAge(21);
        user.setCreationDate(new Date());
        user.setLastUpdateDate(new Date());
        userMapper.insertByUser(user);

        User u = userMapper.findByName("tom");

        assertThat(u.getAge(), is(21));
    }

    @Test
    public void insertByMap() {
        // 1.insert:  insert一条数据，并select出来验证
        Map<String, Object> map = new HashMap<>();
        map.put("name", "tom");
        map.put("age", 21);
        userMapper.insertByMap(map);

        User u = userMapper.findByName("tom");

        assertThat(u.getAge(), is(21));
    }


}