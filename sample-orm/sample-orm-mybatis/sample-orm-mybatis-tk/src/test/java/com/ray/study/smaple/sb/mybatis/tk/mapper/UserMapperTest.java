package com.ray.study.smaple.sb.mybatis.tk.mapper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ray.study.smaple.sb.mybatis.tk.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;

/**
 * UserMapperTest
 *
 * @author ray
 * @date 2020/3/10
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@Rollback
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testUserMapper() {
        //1. 测试插入，主键回写
        for(int i = 0; i < 30; i++) {
            User user = new User();
            user.setUsername("tom"+i);
            user.setEmail("tom"+i+"@qq.com");
            user.setPassword("password");
            user.setAge(20+i);

            userMapper.insertSelective(user);
            assertThat(user.getId(), is(notNullValue()));
        }

        //2. 测试手写sql
        User  user0 =userMapper.findByUsername("tom0");
        assertThat(user0, is(notNullValue()));


        // 3.分页+排序
        int pageSize =10;
        // 3.1 lambda 写法
        final PageInfo<User> pageInfo = PageHelper
                .startPage(1, pageSize)
                .setOrderBy("id desc")
                .doSelectPageInfo(() -> this.userMapper.selectAll());
        assertThat(pageInfo.getSize(), lessThanOrEqualTo(pageSize));

        // 3.2 普通写法
        PageHelper.startPage(1, pageSize).setOrderBy("id desc");
        List<User> userList = userMapper.selectAll();
        final PageInfo<User> userPageInfo = new PageInfo<>(userList);
        assertThat(userPageInfo.getSize(), lessThanOrEqualTo(pageSize));

        userMapper.selectAll().forEach(user -> userMapper.delete(user));
    }
}