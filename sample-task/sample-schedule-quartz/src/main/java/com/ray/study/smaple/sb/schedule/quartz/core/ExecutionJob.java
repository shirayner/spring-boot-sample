package com.ray.study.smaple.sb.schedule.quartz.core;

import com.ray.study.smaple.sb.schedule.quartz.constant.QuartzJobStatusEnum;
import com.ray.study.smaple.sb.schedule.quartz.entity.QuartzJob;
import com.ray.study.smaple.sb.schedule.quartz.entity.QuartzLog;
import com.ray.study.smaple.sb.schedule.quartz.repository.QuartzLogRepository;
import com.ray.study.smaple.sb.schedule.quartz.service.QuartzJobService;
import com.ray.study.smaple.sb.schedule.quartz.util.ThrowableUtil;
import com.ray.study.smaple.sb.schedule.quartz.util.context.SpringContextHolder;
import com.ray.study.smaple.sb.schedule.quartz.util.thread.ThreadPoolHelper;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;
import java.util.concurrent.Future;

/**
 * description
 * 参考人人开源，https://gitee.com/renrenio/renren-security
 *
 * @author r.shi 2020/09/10 11:57
 */
@Async
public class ExecutionJob extends QuartzJobBean {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    @SuppressWarnings("unchecked")
    protected void executeInternal(JobExecutionContext context) {
        QuartzJob quartzJob = (QuartzJob) context.getMergedJobDataMap().get(QuartzJob.JOB_KEY);
        // 获取spring bean
        QuartzLogRepository quartzLogRepository = SpringContextHolder.getBean(QuartzLogRepository.class);
        QuartzJobService quartzJobService = SpringContextHolder.getBean(QuartzJobService.class);

        QuartzLog log = new QuartzLog();
        log.setJobName(quartzJob.getJobName());
        log.setStartTime(LocalDateTime.now());

        long startTime = System.currentTimeMillis();

        try {
            // 执行任务
            logger.info("任务准备执行，任务名称：{}", quartzJob.getJobName());
            QuartzRunnable task = new QuartzRunnable(quartzJob.getJobClassName(), quartzJob.getJobMethodName(), quartzJob.getJobParams());
            Future<?> future = ThreadPoolHelper.getThreadPool().submit(task);
            future.get();
            long times = System.currentTimeMillis() - startTime;

            // log
            log.setFinishTime(LocalDateTime.now());
            log.setTime(times);
            log.setIsSuccess(true);
            logger.info("任务执行完毕，任务名称：{} 总共耗时：{} 毫秒", quartzJob.getJobName(), times);
        } catch (Exception e) {
            logger.error("任务执行失败，任务名称：{}" + quartzJob.getJobName(), e);
            long times = System.currentTimeMillis() - startTime;

            log.setTime(times);
            // 任务状态 0：成功 1：失败
            log.setIsSuccess(false);
            log.setExceptionDetail(ThrowableUtil.getStackTrace(e));
            quartzJob.setJobStatus(QuartzJobStatusEnum.STOPED.getStatus());
            //更新状态
            quartzJobService.updateIsPause(quartzJob);
        } finally {
            quartzLogRepository.save(log);
        }
    }
}


