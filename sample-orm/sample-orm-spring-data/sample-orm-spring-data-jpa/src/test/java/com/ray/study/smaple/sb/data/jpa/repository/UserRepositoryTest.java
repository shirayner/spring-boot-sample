package com.ray.study.smaple.sb.data.jpa.repository;

import com.ray.study.smaple.sb.data.jpa.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


/**
 * UserRepositoryTest
 *
 * @author ray
 * @date 2020/3/16
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;


    /**
     * 查询用户
     */
    @Test
    public void queryUser(){

        List<User> userToAddList = new ArrayList<>();
        userToAddList.add(new User("aatomcat000","password", "aatomcat000@qq.com", 20));
        userToAddList.add(new User("tomcat","password", "tomcat@qq.com",21));
        userToAddList.add(new User("tttomcat","password", "tttomcat@qq.com",22));
        userToAddList.add(new User("tttomcat111","password", "tttomcat111@qq.com",23));
        userToAddList.add(new User("tomcataaa","password", "tomcataaa@qq.com",24));

        // 新增用户
        userToAddList.forEach(user -> {
            userRepository.save(user);
        });


        // 查询所有名字为tom的用户
        List<User> userList = userRepository.findAllByUsername("tom");
        // assertThat(userList.size(), is(1));
        System.out.println(userList);

        // 查询所有年龄大于等于 21 的用户
        List<User> userList2 = userRepository.findByAgeGreaterThanEqualOrderById(21);
        //assertThat(userList2.size(), is(notNullValue()));
        System.out.println(userList2);

        // 精确查询，这里的like 并不是模糊查询
        List<User> userList3 = userRepository.findByUsernameLike("tom");
        //assertThat(userList3.size(), is(notNullValue()));
        System.out.println(userList3);

        // 模糊查询: Containing才是模糊查询
        List<User> userList4 = userRepository.findByUsernameContaining("tom");
        //assertThat(userList4.size(), is(notNullValue()));
        System.out.println(userList4);

        userRepository.deleteAll();
    }


    /**
     * 新增用户
     */
    @Test
    public void insertUser(){

        User user = new User();
        user.setUsername("tom");
        user.setPassword("password");
        user.setEmail("tom@qq.com");
        user.setAge(21);
        User user1 = userRepository.save(user);

        assertThat(user.getId(),is(notNullValue()));

        userRepository.deleteAll();
    }


    /**
     * 更新用户
     */
    @Test
    public void updateUser(){

        User user = new User();
        user.setUsername("tom2");
        user.setAge(1212);
        User user1 = userRepository.save(user);

        Optional<User> optionalUser = userRepository.findById(user1.getId());
        User updatedUser = null;
        if(optionalUser.isPresent()){
            updatedUser = optionalUser.get();
        }

        assertThat(updatedUser.getAge(),is(1212));

        userRepository.deleteAll();
    }


    /**
     * 删除用户
     */
    @Test
    public void deleteUser(){
        User user = new User();
        user.setUsername("tom2");
        userRepository.save(user);
        List<User> userList = userRepository.findAllByUsername("tom2");
        Assertions.assertNotEquals(0, userList.size());

        userRepository.delete(user);

        userList = userRepository.findAllByUsername("tom2");
        Assertions.assertEquals(0, userList.size());

    }

}