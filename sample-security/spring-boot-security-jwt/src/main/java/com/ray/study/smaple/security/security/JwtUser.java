package com.ray.study.smaple.security.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

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

    private Integer id;

    public JwtUser(Integer id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
    }

}