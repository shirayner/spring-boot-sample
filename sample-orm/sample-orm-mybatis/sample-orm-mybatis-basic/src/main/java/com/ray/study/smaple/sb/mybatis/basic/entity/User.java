package com.ray.study.smaple.sb.mybatis.basic.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * description
 *
 * @author shira 2019/05/09 21:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

	private Long id;

	private String username;

	private String password;

	private String email;

	private Integer age;

	private LocalDateTime createTime;

	private LocalDateTime updateTime;

}
