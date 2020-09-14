package com.ray.study.smaple.sb.schedule.quartz.service;

import com.ray.study.smaple.sb.schedule.quartz.entity.QuartzJob;

import java.util.Set;

/**
 * description
 *
 * @author r.shi 2020/09/11 16:18
 */
public interface QuartzJobService {

    /**
     * 创建
     * @param resources /
     * @return /
     */
    QuartzJob create(QuartzJob resources);

    /**
     * 编辑
     * @param resources /
     */
    void update(QuartzJob resources);

    /**
     * 更改定时任务状态
     * @param quartzJob /
     */
    void updateIsPause(QuartzJob quartzJob);

    /**
     * 删除任务
     * @param ids /
     */
    void delete(Set<Long> ids);

    /**
     * 根据ID查询
     * @param id ID
     * @return /
     */
    QuartzJob findById(Long id);


    /**
     * 立即执行定时任务
     * @param quartzJob /
     */
    void execution(QuartzJob quartzJob);

}
