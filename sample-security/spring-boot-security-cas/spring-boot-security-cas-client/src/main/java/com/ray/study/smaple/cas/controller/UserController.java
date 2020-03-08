package com.ray.study.smaple.cas.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	
	@RequestMapping("/login")
	@PreAuthorize("hasRole('USER')")
	public String login() {
		
		return "login";
	}
	
	@PreAuthorize("hasRole('USER')")
	@RequestMapping("/logout")
	public String logout() {
		
		return "logout";
	}
	
	@RequestMapping("/index")
	public String index() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return "index";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping("/admin")
	public String welcom() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return "admin";
	}


}
