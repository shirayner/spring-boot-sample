package com.ray.study.smaple.beanmap.mapstruct.converter;

import com.ray.study.smaple.beanmap.mapstruct.dto.RoleDTO;
import com.ray.study.smaple.beanmap.mapstruct.entity.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * description
 *
 * @author r.shi 2021/5/26 15:51
 */
public class RoleConverterTest {

    @Test
    public void toD() {
        Role role = mockRole();
        RoleDTO roleDTO = RoleConverter.INSTANCE.toE(role);
        System.out.println(roleDTO); // RoleDTO(roleId=1, roleCode=ADMIN)
        Assertions.assertNotNull(roleDTO.getRoleId());
    }

    @Test
    public void toE() {
    }

    @Test
    public void testToD() {
        List<Role> roles = mockRoleList();
        List<RoleDTO> roleDTOS = RoleConverter.INSTANCE.toE(roles);
        System.out.println(roleDTOS); // [RoleDTO(roleId=1, roleCode=ADMIN)]
        Assertions.assertNotEquals(0, roleDTOS.size());
    }

    @Test
    public void testToE() {
    }

    private static List<Role> mockRoleList() {
        List<Role> roles = new ArrayList<>();
        roles.add(mockRole());
        return roles;
    }


    private static Role mockRole() {
        Role role = new Role();
        role.setRoleId(1L);
        role.setRoleCode("ADMIN");
        return role;
    }
}