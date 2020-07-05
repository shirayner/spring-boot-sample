package com.ray.study.smaple.sb.elasticsearch.elk.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HelloController
 *
 * @author ray
 * @date 2020/6/30
 */
@RestController
@RequestMapping("/hello")
@Slf4j
public class HelloController {

    @GetMapping("/{name}")
    public String hi(@PathVariable(value = "name") String name) {
        log.info( "name = {}" , name );
        return "hi , " + name;
    }

}
