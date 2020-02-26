package com.ray.study.smaple.security.security;

import com.ray.study.smaple.security.entity.UserDO;
import com.ray.study.smaple.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * JwtUserDetailsServiceImpl
 *
 * @author ray
 * @date 2020/2/26
 */
@Component
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDO user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User '" + username + "' not found");
        }

        List<SimpleGrantedAuthority> grantedAuthorities = user.getRoleList().stream().map(roleDO -> new SimpleGrantedAuthority(roleDO.getRoleCode())).collect(Collectors.toList());
        return new JwtUser(user.getId(), user.getUsername(), user.getPassword(), grantedAuthorities);
    }

}
