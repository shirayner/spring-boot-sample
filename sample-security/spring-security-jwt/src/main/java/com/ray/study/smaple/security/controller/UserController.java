package com.ray.study.smaple.security.controller;

import com.ray.study.smaple.security.entity.UserDO;
import com.ray.study.smaple.security.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

/**
 * UserController
 *
 * @author ray
 * @date 2020/2/26
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/list")
    public String getToken() throws AuthenticationException {
        return "list";
    }


    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 操作结果
     * @throws AuthenticationException 错误信息
     */
    @PostMapping(value = "/login")
    public String getToken(String username, String password) throws AuthenticationException {
        return userService.login(username, password);
    }

    /**
     * 用户注册
     *
     * @param user          用户信息
     * @return 操作结果
     * @throws AuthenticationException 错误信息
     */
    @PostMapping(value = "/register")
    public String register(UserDO user) throws AuthenticationException {
        return userService.register(user);
    }

    /**
     * 刷新密钥
     *
     * @param authorization 原密钥
     * @return 新密钥
     * @throws AuthenticationException 错误信息
     */
    @GetMapping(value = "/refreshToken")
    public String refreshToken(@RequestHeader String authorization) throws AuthenticationException {
        return userService.refreshToken(authorization);
    }

}