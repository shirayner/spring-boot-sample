package com.ray.study.smaple.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * ConcurrentmapCacheApplication
 *
 * @author ray
 * @date 2020/3/19
 */
@SpringBootApplication
public class ConcurrentMapCacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConcurrentMapCacheApplication.class, args);
    }
}