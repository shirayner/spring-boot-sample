package com.ray.study.smaple.beanmap.mapstruct.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * description
 *
 * @author r.shi 2021/5/26 15:36
 */
@Data
public class User {
    private Long id;
    private String userName;
    private Boolean userStatus;
    private Date createTime;
    private List<Role> roles;
}
