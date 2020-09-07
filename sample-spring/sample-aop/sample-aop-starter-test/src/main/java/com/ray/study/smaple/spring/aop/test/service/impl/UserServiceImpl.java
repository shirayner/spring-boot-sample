package com.ray.study.smaple.spring.aop.test.service.impl;

import com.ray.study.smaple.spring.aop.test.service.UserService;
import org.springframework.stereotype.Service;

/**
 * UserServiceImpl
 *
 * @author ray
 * @date 2020/9/7
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public void testAop() {
        System.out.println("=================== UserServiceImpl.testAop run ======================= ");
    }
}
