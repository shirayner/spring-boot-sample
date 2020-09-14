package com.ray.study.smaple.sb.schedule.quartz.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * description
 *
 * @author r.shi 2020/09/11 19:08
 */
@Slf4j
@Component
public class TestTask {

    public void run() {
        log.info("执行成功");
    }

    public void run1(String param) {
        log.info("执行成功，参数为：param: {} ", param);
    }
}
