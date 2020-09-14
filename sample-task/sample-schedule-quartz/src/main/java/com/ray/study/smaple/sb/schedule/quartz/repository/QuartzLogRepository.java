package com.ray.study.smaple.sb.schedule.quartz.repository;

import com.ray.study.smaple.sb.schedule.quartz.entity.QuartzLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * description
 *
 * @author r.shi 2020/09/11 16:16
 */
@Repository
public interface QuartzLogRepository extends JpaRepository<QuartzLog,Long>, JpaSpecificationExecutor<QuartzLog> {

}
