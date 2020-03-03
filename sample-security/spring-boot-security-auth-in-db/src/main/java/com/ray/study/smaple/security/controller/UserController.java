package com.ray.study.smaple.security.controller;

import com.ray.study.smaple.security.entity.UserDO;
import com.ray.study.smaple.security.repository.RoleRepository;
import com.ray.study.smaple.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * UserController
 *
 * @author ray
 * @date 2020/2/25
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @RequestMapping("/add")
    public String registry(UserDO user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(user.getUsername().startsWith("admin")){
            user.setRoleList(roleRepository.findByRoleName("ROLE_ADMIN"));
        }else{
            user.setRoleList(roleRepository.findByRoleName("ROLE_USER"));
        }
        userRepository.save(user);
        return "login?addSuccess";
    }



}