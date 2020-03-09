package com.ray.study.smaple.sb.common.dto;

import com.ray.study.smaple.sb.common.validate.Groups;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * UserDto
 *
 * @author ray
 * @date 2020/3/9
 */
@Data
public class UserDto {
    @NotNull(message = "id 不能为空", groups = Groups.Update.class)
    private String id;

    @NotNull(message = "用户名不能为空")
    @Size(min = 6, max = 11, message = "用户名长度必须是6-11个字符")
    private String username;

    @NotNull(message = "用户密码不能为空")
    @Size(min = 6, max = 11, message = "密码长度必须是6-16个字符")
    private String password;

    @NotNull(message = "用户邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;
}
