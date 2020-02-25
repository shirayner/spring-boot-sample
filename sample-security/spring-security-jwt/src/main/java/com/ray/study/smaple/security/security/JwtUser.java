package com.ray.study.smaple.security.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

/**
 * JwtUser
 *
 * @author ray
 * @date 2020/2/26
 */
@Getter
@Setter
public class JwtUser extends User {

    private String id;

    public JwtUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}