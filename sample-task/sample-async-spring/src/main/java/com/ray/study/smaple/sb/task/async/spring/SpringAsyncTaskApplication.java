package com.ray.study.smaple.sb.task.async.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * ElkApplication
 *
 * @author ray
 * @date 2020/6/30
 */
@SpringBootApplication
@EnableAsync
public class SpringAsyncTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAsyncTaskApplication.class, args);
    }
}
