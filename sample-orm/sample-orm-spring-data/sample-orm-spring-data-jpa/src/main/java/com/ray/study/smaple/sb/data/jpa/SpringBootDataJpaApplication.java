package com.ray.study.smaple.sb.data.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * SpringBootDataJpaApplication
 *
 * @author ray
 * @date 2020/3/10
 */
@EnableJpaAuditing
@SpringBootApplication
public class SpringBootDataJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDataJpaApplication.class, args);
    }

}
