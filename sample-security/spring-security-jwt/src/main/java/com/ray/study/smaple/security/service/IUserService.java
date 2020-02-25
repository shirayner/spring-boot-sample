package com.ray.study.smaple.security.service;

import com.ray.study.smaple.security.entity.UserDO;

/**
 * IUserService
 *
 * @author ray
 * @date 2020/2/26
 */
public interface IUserService {

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 操作结果
     */
    String login(String username, String password);

    /**
     * 用户注册
     *
     * @param user 用户信息
     * @return 操作结果
     */
    String register(UserDO user);

    /**
     * 刷新密钥
     *
     * @param oldToken 原密钥
     * @return 新密钥
     */
    String refreshToken(String oldToken);
}
