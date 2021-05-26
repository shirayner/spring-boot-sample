package com.ray.study.smaple.beanmap.mapstruct.dto;

import lombok.Data;

import java.util.List;

/**
 * description
 *
 * @author r.shi 2021/5/26 15:37
 */
@Data
public class UserDTO {
    private Long id;
    private String userName;
    private Boolean userStatus;
    private Long createTime;

    private List<RoleDTO> roles;
}
