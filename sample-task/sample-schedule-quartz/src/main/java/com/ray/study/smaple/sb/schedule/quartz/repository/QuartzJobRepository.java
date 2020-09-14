package com.ray.study.smaple.sb.schedule.quartz.repository;

import com.ray.study.smaple.sb.schedule.quartz.entity.QuartzJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * description
 *
 * @author r.shi 2020/09/11 16:16
 */
@Repository
public interface QuartzJobRepository extends JpaRepository<QuartzJob,Long>, JpaSpecificationExecutor<QuartzJob> {

    /**
     * 查询启用的任务
     * @return List
     */
    List<QuartzJob> findByJobStatus(Integer jobStatus);
}
