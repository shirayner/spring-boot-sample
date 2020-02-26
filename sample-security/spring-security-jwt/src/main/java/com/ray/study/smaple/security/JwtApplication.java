package com.ray.study.smaple.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Application
 *
 * @author ray
 * @date 2020/2/25
 */
@SpringBootApplication
public class JwtApplication {

    public static void main(String[] args) throws URISyntaxException, IOException {
        SpringApplication.run(JwtApplication.class, args);
    }


}