package com.ray.study.smaple.sb.common;

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
public class ExceptionApplication {

    public static void main(String[] args) throws URISyntaxException, IOException {
        SpringApplication.run(ExceptionApplication.class, args);
    }


}