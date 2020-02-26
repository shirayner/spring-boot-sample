package com.ray.study.smaple.security.service;

import com.ray.study.smaple.security.entity.UserDO;

import javax.servlet.http.HttpServletRequest;

/**
 * IUserService
 *
 * @author ray
 * @date 2020/2/26
 */
public interface IUserService {

    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     * @return
     */
    String login(String username, String password);

    String registry(UserDO user);

    void delete(String username);

    UserDO search(String username);

    UserDO whoami(HttpServletRequest req);

    String refresh(String username);
}
