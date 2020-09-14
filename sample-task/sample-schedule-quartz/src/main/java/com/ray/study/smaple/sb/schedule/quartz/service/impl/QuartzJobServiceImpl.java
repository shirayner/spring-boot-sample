package com.ray.study.smaple.sb.schedule.quartz.service.impl;

import com.ray.study.smaple.sb.schedule.quartz.constant.QuartzJobStatusEnum;
import com.ray.study.smaple.sb.schedule.quartz.core.QuartzManage;
import com.ray.study.smaple.sb.schedule.quartz.entity.QuartzJob;
import com.ray.study.smaple.sb.schedule.quartz.exception.BadRequestException;
import com.ray.study.smaple.sb.schedule.quartz.repository.QuartzJobRepository;
import com.ray.study.smaple.sb.schedule.quartz.repository.QuartzLogRepository;
import com.ray.study.smaple.sb.schedule.quartz.service.QuartzJobService;
import org.quartz.CronExpression;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Set;

/**
 * description
 *
 * @author r.shi 2020/09/11 16:20
 */
@Service(value = "quartzJobService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class QuartzJobServiceImpl implements QuartzJobService {

    private final QuartzJobRepository quartzJobRepository;

    private final QuartzLogRepository quartzLogRepository;

    private final QuartzManage quartzManage;

    public QuartzJobServiceImpl(QuartzJobRepository quartzJobRepository, QuartzLogRepository quartzLogRepository, QuartzManage quartzManage) {
        this.quartzJobRepository = quartzJobRepository;
        this.quartzLogRepository = quartzLogRepository;
        this.quartzManage = quartzManage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public QuartzJob create(QuartzJob quartzJob) {
        if (!CronExpression.isValidExpression(quartzJob.getCronExpression())) {
            throw new BadRequestException("cron表达式格式错误");
        }

        Integer jobStatus = QuartzJobStatusEnum.READY.getStatus();
        if (ObjectUtils.isEmpty(quartzJob.getStartTime())) {
            jobStatus = QuartzJobStatusEnum.STARTED.getStatus();
        }
        quartzJob.setJobStatus(jobStatus);
        quartzJob = quartzJobRepository.save(quartzJob);
        quartzManage.addJob(quartzJob);
        return quartzJob;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(QuartzJob resources) {
        if (resources.getId().equals(1L)) {
            throw new BadRequestException("该任务不可操作");
        }
        if (!CronExpression.isValidExpression(resources.getCronExpression())) {
            throw new BadRequestException("cron表达式格式错误");
        }
        resources = quartzJobRepository.save(resources);
        quartzManage.updateJobCron(resources);
    }


    @Override
    public void updateIsPause(QuartzJob quartzJob) {
        if (quartzJob.isPause()) {
            quartzManage.pauseJob(quartzJob);
        } else {
            quartzManage.resumeJob(quartzJob);
        }
        quartzJobRepository.save(quartzJob);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        for (Long id : ids) {
            QuartzJob quartzJob = findById(id);
            quartzManage.deleteJob(quartzJob);
            quartzJobRepository.delete(quartzJob);
        }
    }

    @Override
    public QuartzJob findById(Long id) {
        QuartzJob quartzJob = quartzJobRepository.findById(id).orElseGet(QuartzJob::new);
        return quartzJob;
    }

    @Override
    public void execution(QuartzJob quartzJob) {
        if (quartzJob.getId().equals(1L)) {
            throw new BadRequestException("该任务不可操作");
        }
        quartzManage.runJobNow(quartzJob);
    }
}
