package com.ray.study.smaple.cas.security;

import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CustomUserDetailsService implements AuthenticationUserDetailsService<CasAssertionAuthenticationToken> {

	
	@Override
	public UserDetails loadUserDetails(CasAssertionAuthenticationToken token) throws UsernameNotFoundException {
		
		System.out.println("当前认证成功的用户名:"+token.getName());
		
		//加载用户权限信息，注意这里已经由cas完成认证，不再需要加载密码了

		//准备权限集合
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		//注意这里user中的密码可以不写，因为cas已经验证过了，现在只是需要把该用户的权限注入security中，但是也不能是null，null就会报错
	    return new User(token.getName(), "", grantedAuthorities);
	    
	}
	
	

}
