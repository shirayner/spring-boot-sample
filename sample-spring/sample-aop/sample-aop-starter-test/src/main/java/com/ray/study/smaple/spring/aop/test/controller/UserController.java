package com.ray.study.smaple.spring.aop.test.controller;

import com.ray.study.smaple.spring.aop.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController
 *
 * @author ray
 * @date 2020/9/7
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    public String index(){
        userService.testAop();
        return "success";
    }

}
