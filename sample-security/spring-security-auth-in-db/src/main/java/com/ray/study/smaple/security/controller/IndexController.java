package com.ray.study.smaple.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * HomeController
 *
 * @author ray
 * @date 2020/2/25
 */
@Controller
public class IndexController {

    @RequestMapping("/registry")
    public String signUp() {
        return "registry";
    }

    @RequestMapping("/login")
    public String login() {
       return "login";
    }

    @RequestMapping("/home")
    @ResponseBody
    public String home() {
        return "login?home";
    }

}
