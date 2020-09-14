package com.ray.study.smaple.sb.schedule.quartz.entity;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * description
 *
 * @author r.shi 2020/09/11 14:30
 */
@Entity
@Data
@Table(name = "quartz_log")
@EntityListeners(AuditingEntityListener.class)   // 监听实体类变更
public class QuartzLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 任务名称 */
    private String jobName;

    /** 开始时间 */
    private LocalDateTime startTime;

    /** 开始时间 */
    private LocalDateTime finishTime;

    /** 状态 */
    private Boolean isSuccess;

    /** 异常详细 */
    @Size(max=1000)
    private String exceptionDetail;

    /** 耗时（毫秒） */
    private Long time;

}
