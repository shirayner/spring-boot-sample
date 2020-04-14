package com.ray.study.smaple.spring.mvc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * User
 *
 * @author ray
 * @date 2020/3/16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
@DynamicInsert
public class User extends BaseEntity{

    private String username;

    private String password;

    private String email;

    private Integer age;

}

