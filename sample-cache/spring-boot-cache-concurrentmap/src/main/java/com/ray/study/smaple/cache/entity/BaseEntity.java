package com.ray.study.smaple.cache.entity;


import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * BaseEntity
 *
 * @author shira 2019/05/08 17:17
 */
@Data
@MappedSuperclass  // 声明子类可继承基类字段
@EntityListeners(AuditingEntityListener.class)   // 监听实体类变更
public class BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@CreatedDate  // 自动填充 CreatedDate
	private LocalDateTime createTime;

	@LastModifiedDate // 自动填充 LastModifiedDate
	private LocalDateTime updateTime;
}

