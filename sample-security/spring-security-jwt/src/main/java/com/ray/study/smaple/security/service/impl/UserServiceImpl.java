package com.ray.study.smaple.security.service.impl;

import com.ray.study.smaple.security.entity.RoleDO;
import com.ray.study.smaple.security.entity.UserDO;
import com.ray.study.smaple.security.repository.UserRepository;
import com.ray.study.smaple.security.service.IUserService;
import com.ray.study.smaple.security.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.List;

/**
 * UserServiceImpl
 *
 * @author ray
 * @date 2020/2/26
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String login(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("密码不正确");
        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);


        return jwtTokenUtil.generateToken(userDetails);
    }

    @Override
    public String register(UserDO user) {
        String username = user.getUsername();
        if (userRepository.findByUsername(username) != null) {
            return "用户已存在";
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = user.getPassword();
        user.setPassword(encoder.encode(rawPassword));

        List<RoleDO> roles = new ArrayList<>();
        RoleDO roleDO = new RoleDO();
        roleDO.setId(1);
        roleDO.setRoleCode("ROLE_USER");
        roleDO.setRoleName("普通用户");
        roles.add(roleDO);
        user.setRoleList(roles);
        userRepository.save(user);
        return "success";
    }

    @Override
    public String refreshToken(String oldToken) {
        String token = oldToken.substring("Bearer ".length());

        if (!jwtTokenUtil.isTokenExpired(token)) {
            return jwtTokenUtil.refreshToken(token);
        }
        return "error";
    }

}

