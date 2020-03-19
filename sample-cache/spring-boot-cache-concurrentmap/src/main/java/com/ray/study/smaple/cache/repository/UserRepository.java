package com.ray.study.smaple.cache.repository;

import com.ray.study.smaple.cache.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserRepository
 *
 * @author ray
 * @date 2020/3/16
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 查询年龄大于等于指定年龄的用户
     * @param age 年龄
     * @return userList
     */
    List<User> findByAgeGreaterThanEqualOrderById(Integer age);

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return userList
     */
    List<User> findAllByUsername(String username);

    User findByUsername(String username);


    /**
     * 根据用户名模糊查询
     * @param username username
     * @return userList
     */
    List<User> findByUsernameLike(String username);

    /**
     * 模糊查询
     * @param username
     * @return
     */
    List<User> findByUsernameContaining(String username);
}
