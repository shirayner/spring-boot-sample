package com.ray.study.smaple.sb.schedule.quartz.config;

import com.ray.study.smaple.sb.schedule.quartz.constant.QuartzJobStatusEnum;
import com.ray.study.smaple.sb.schedule.quartz.core.QuartzManage;
import com.ray.study.smaple.sb.schedule.quartz.entity.QuartzJob;
import com.ray.study.smaple.sb.schedule.quartz.repository.QuartzJobRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * description
 *
 * @author r.shi 2020/09/10 11:52
 */
@Component
public class JobRunner implements ApplicationRunner {

    private final QuartzJobRepository quartzJobRepository;

    private final QuartzManage quartzManage;

    public JobRunner(QuartzJobRepository quartzJobRepository, QuartzManage quartzManage) {
        this.quartzJobRepository = quartzJobRepository;
        this.quartzManage = quartzManage;
    }

    /**
     * 项目启动时重新激活启用的定时任务
     * @param applicationArguments /
     */
    @Override
    public void run(ApplicationArguments applicationArguments){
        System.out.println("--------------------注入定时任务---------------------");
        List<QuartzJob> quartzJobs = quartzJobRepository.findByJobStatus(QuartzJobStatusEnum.STARTED.getStatus());
        quartzJobs.forEach(quartzManage::addJob);
        System.out.println("--------------------定时任务注入完成---------------------");
    }
}