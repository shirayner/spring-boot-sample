package com.ray.study.smaple.ratelimit.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 * 启动器
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019/9/30 09:32
 */
@SpringBootApplication
public class RateLimitRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(RateLimitRedisApplication.class, args);
    }

}
