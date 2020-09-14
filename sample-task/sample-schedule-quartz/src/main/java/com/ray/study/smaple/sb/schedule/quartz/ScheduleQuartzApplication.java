package com.ray.study.smaple.sb.schedule.quartz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * description
 *
 * @author r.shi 2020/09/10 11:38
 */
@SpringBootApplication
@EnableJpaAuditing
public class ScheduleQuartzApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScheduleQuartzApplication.class, args);
    }

}
