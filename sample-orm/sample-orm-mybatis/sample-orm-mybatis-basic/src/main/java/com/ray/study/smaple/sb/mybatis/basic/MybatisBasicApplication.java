package com.ray.study.smaple.sb.mybatis.basic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * MybatisBasicApplication
 *
 * @author ray
 * @date 2020/3/10
 */
@MapperScan("com.ray.study.smaple.sb.mybatis.basic.mapper")
@SpringBootApplication
public class MybatisBasicApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisBasicApplication.class, args);
    }

}
