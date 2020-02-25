package com.ray.study.smaple.security.security;

import com.ray.study.smaple.security.entity.RoleDO;
import com.ray.study.smaple.security.entity.UserDO;
import com.ray.study.smaple.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * UserDetailsServiceImpl
 *
 * @author ray
 * @date 2020/2/25
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDO userDO = userRepository.findByUsername(username);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (RoleDO roleDO : userDO.getRoleList()) {
            authorities.add(new SimpleGrantedAuthority(roleDO.getRoleCode()));
        }
        return new User(userDO.getUsername(), userDO.getPassword(), authorities);
    }
}
