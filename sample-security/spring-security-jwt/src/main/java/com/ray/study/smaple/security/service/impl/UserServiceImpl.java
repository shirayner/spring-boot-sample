package com.ray.study.smaple.security.service.impl;

import com.ray.study.smaple.security.entity.UserDO;
import com.ray.study.smaple.security.exception.CustomException;
import com.ray.study.smaple.security.repository.UserRepository;
import com.ray.study.smaple.security.security.JwtUser;
import com.ray.study.smaple.security.service.IUserService;
import com.ray.study.smaple.security.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * UserServiceImpl
 *
 * @author ray
 * @date 2020/2/26
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    public String login(String username, String password) {
        Authentication authentication = null;
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername()去验证用户名和密码，
            // 如果正确，则存储该用户名密码到security 的 context中
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        //存储认证信息
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //生成token
        JwtUser userDetail = (JwtUser) authentication.getPrincipal();
        return jwtTokenUtil.generateToken(userDetail);
    }

    @Override
    public String registry(UserDO user) {
        if (!userRepository.existsByUsername(user.getUsername())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return "success";
        } else {
            throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Override
    public void delete(String username) {
        userRepository.deleteByUsername(username);
    }

    @Override
    public UserDO search(String username) {
        UserDO user = userRepository.findByUsername(username);
        if (user == null) {
            throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
        }
        return user;
    }

    @Override
    public UserDO whoami(HttpServletRequest req) {
        JwtUser jwtUser = jwtTokenUtil.getUserFromToken(req);
        return userRepository.findByUsername(jwtUser.getUsername());
    }

    @Override
    public String refresh(String accessToken) {
        return jwtTokenUtil.refreshToken(accessToken);
    }
}

