package com.ray.study.smaple.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sun.plugin.services.BrowserService;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Application
 *
 * @author ray
 * @date 2020/2/25
 */
@SpringBootApplication
public class AuthInDbApplication {

    public static void main(String[] args) throws URISyntaxException, IOException {
        SpringApplication.run(AuthInDbApplication.class, args);
    }

}