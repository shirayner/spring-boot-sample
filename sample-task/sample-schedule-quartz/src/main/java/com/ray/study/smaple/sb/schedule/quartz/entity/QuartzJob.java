package com.ray.study.smaple.sb.schedule.quartz.entity;

import com.ray.study.smaple.sb.schedule.quartz.constant.QuartzJobStatusEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * description
 *
 * @author r.shi 2020/09/11 14:29
 */
@Getter
@Setter
@Entity
@Table(name = "quartz_job")
@EntityListeners(AuditingEntityListener.class)   // 监听实体类变更
public class QuartzJob  implements Serializable {

    public static final String JOB_KEY = "JOB_KEY";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 定时器名称 */
    private String jobName;

    /** 定时器分组名 */
    private String jobGroup;

    /** 定时器描述 */
    private String description;

    /** 类名 */
    private String jobClassName;

    /** 方法名称 */
    private String jobMethodName;

    /** 参数 */
    private String jobParams;

    /** cron表达式 */
    private String cronExpression;

    /** 状态 */
    private Integer jobStatus;

    /** 开始时间 */
    private LocalDateTime startTime;

    /** 开始时间 */
    private LocalDateTime endTime;

    @CreatedDate
    private LocalDateTime createTime;


    public boolean isPause(){
      if(this.jobStatus != null && this.getJobStatus() == QuartzJobStatusEnum.STOPED.getStatus()){
          return true;
      }

      return false;
    }

}