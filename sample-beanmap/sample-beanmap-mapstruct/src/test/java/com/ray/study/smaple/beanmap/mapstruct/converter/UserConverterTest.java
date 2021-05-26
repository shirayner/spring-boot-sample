package com.ray.study.smaple.beanmap.mapstruct.converter;

import com.ray.study.smaple.beanmap.mapstruct.dto.UserDTO;
import com.ray.study.smaple.beanmap.mapstruct.entity.Role;
import com.ray.study.smaple.beanmap.mapstruct.entity.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * description
 *
 * @author r.shi 2021/5/26 15:51
 */
public class UserConverterTest {

    @Test
    public void toD() {
        User user = mockUser();
        UserDTO userDTO = UserConverter.INSTANCE.toE(user);
        System.out.println(userDTO);
        Assert.assertNotNull(user.getId());
    }

    @Test
    public void toE() {
    }

    @Test
    public void testToD() {
    }

    @Test
    public void testToE() {
    }


    private static User mockUser() {
        User user = new User();
        user.setId(1L);
        user.setUserName("tom");
        user.setUserStatus(Boolean.TRUE);
        user.setCreateTime(new Date());

        List<Role> roles = new ArrayList<>();
        roles.add(mockRole());

        user.setRoles(roles);
        return user;
    }

    private static Role mockRole() {
        Role role = new Role();
        role.setRoleId(1L);
        role.setRoleCode("ADMIN");
        return role;
    }
}