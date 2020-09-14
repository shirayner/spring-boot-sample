package com.ray.study.smaple.sb.schedule.quartz.core;

import com.ray.study.smaple.sb.schedule.quartz.constant.QuartzConstant;
import com.ray.study.smaple.sb.schedule.quartz.entity.QuartzJob;
import com.ray.study.smaple.sb.schedule.quartz.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.TriggerKey;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.ZoneId;
import java.util.Date;

import static org.quartz.TriggerBuilder.newTrigger;

/**
 * description
 *
 * @author r.shi 2020/09/10 11:53
 */
@Slf4j
@Component
public class QuartzManage {

    @Resource(name = "scheduler")
    private Scheduler scheduler;

    public void addJob(QuartzJob quartzJob) {
        try {
            String jobName = QuartzConstant.JOB_NAME_PREFIX + quartzJob.getId();
            String cronExpression = quartzJob.getCronExpression();

            // 构建job信息
            JobDetail jobDetail = JobBuilder.newJob(ExecutionJob.class).
                    withIdentity(jobName).build();

            //通过触发器名和cron 表达式创建 Trigger
            CronTriggerImpl cronTrigger = (CronTriggerImpl) newTrigger().withIdentity(jobName).startNow().withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).build();

            cronTrigger.getJobDataMap().put(QuartzConstant.JOB_KEY, quartzJob);

            //设置启动和终止时间
            setTime(cronTrigger, quartzJob);

            //执行定时任务
            scheduler.scheduleJob(jobDetail, cronTrigger);

            // 暂停任务
            if (quartzJob.isPause()) {
                pauseJob(quartzJob);
            }
        } catch (Exception e) {
            log.error("创建定时任务失败", e);
            throw new BadRequestException("创建定时任务失败");
        }
    }

    /**
     * 更新job cron表达式
     *
     * @param quartzJob /
     */
    public void updateJobCron(QuartzJob quartzJob) {
        try {
            String jobName = QuartzConstant.JOB_NAME_PREFIX + quartzJob.getId();
            String cronExpression = quartzJob.getCronExpression();

            // get trigger
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName);
            CronTriggerImpl cronTrigger = (CronTriggerImpl) scheduler.getTrigger(triggerKey);

            if (cronTrigger == null) {
                // 如果不存在则创建一个定时任务
                addJob(quartzJob);
                cronTrigger = (CronTriggerImpl) scheduler.getTrigger(triggerKey);
            }
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
            cronTrigger =  (CronTriggerImpl) cronTrigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

            //设置启动和终止时间
            setTime(cronTrigger, quartzJob);


            cronTrigger.getJobDataMap().put(QuartzConstant.JOB_KEY, quartzJob);

            scheduler.rescheduleJob(triggerKey, cronTrigger);
            // 暂停任务
            if (quartzJob.isPause()) {
                pauseJob(quartzJob);
            }
        } catch (Exception e) {
            log.error("更新定时任务失败", e);
            throw new BadRequestException("更新定时任务失败");
        }

    }

    /**
     * 删除一个job
     *
     * @param quartzJob /
     */
    public void deleteJob(QuartzJob quartzJob) {
        try {
            String jobName = QuartzConstant.JOB_NAME_PREFIX + quartzJob.getId();

            JobKey jobKey = JobKey.jobKey(jobName);
            scheduler.pauseJob(jobKey);
            scheduler.deleteJob(jobKey);
        } catch (Exception e) {
            log.error("删除定时任务失败", e);
            throw new BadRequestException("删除定时任务失败");
        }
    }

    /**
     * 恢复一个job
     *
     * @param quartzJob /
     */
    public void resumeJob(QuartzJob quartzJob) {
        try {
            String jobName = QuartzConstant.JOB_NAME_PREFIX + quartzJob.getId();
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            // 如果不存在则创建一个定时任务
            if (trigger == null) {
                addJob(quartzJob);
            }
            JobKey jobKey = JobKey.jobKey(jobName);
            scheduler.resumeJob(jobKey);
        } catch (Exception e) {
            log.error("恢复定时任务失败", e);
            throw new BadRequestException("恢复定时任务失败");
        }
    }

    /**
     * 立即执行job
     *
     * @param quartzJob /
     */
    public void runJobNow(QuartzJob quartzJob) {
        try {
            String jobName = QuartzConstant.JOB_NAME_PREFIX + quartzJob.getId();

            TriggerKey triggerKey = TriggerKey.triggerKey(jobName);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            // 如果不存在则创建一个定时任务
            if (trigger == null) {
                addJob(quartzJob);
            }
            JobDataMap dataMap = new JobDataMap();
            dataMap.put(QuartzConstant.JOB_KEY, quartzJob);
            JobKey jobKey = JobKey.jobKey(jobName);
            scheduler.triggerJob(jobKey, dataMap);
        } catch (Exception e) {
            log.error("定时任务执行失败", e);
            throw new BadRequestException("定时任务执行失败");
        }
    }

    /**
     * 暂停一个job
     *
     * @param quartzJob /
     */
    public void pauseJob(QuartzJob quartzJob) {
        try {
            String jobName = QuartzConstant.JOB_NAME_PREFIX + quartzJob.getId();
            JobKey jobKey = JobKey.jobKey(jobName);
            scheduler.pauseJob(jobKey);
        } catch (Exception e) {
            log.error("定时任务暂停失败", e);
            throw new BadRequestException("定时任务暂停失败");
        }
    }


    private void setTime(CronTriggerImpl cronTrigger, QuartzJob quartzJob) {
        Date startTime = new Date();
        if (quartzJob.getStartTime() != null) {
            startTime = Date.from(quartzJob.getStartTime().atZone(ZoneId.systemDefault()).toInstant());
        }
        cronTrigger.setStartTime(startTime);
        if (quartzJob.getEndTime() != null) {
            cronTrigger.setEndTime(Date.from(quartzJob.getEndTime().atZone(ZoneId.systemDefault()).toInstant()));
        }
    }
}
