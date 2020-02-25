package com.ray.study.smaple.security.repository;


import com.ray.study.smaple.security.entity.RoleDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * RoleRepository
 *
 * @author shira 2019/09/02 19:11
 */
@Repository
public interface RoleRepository extends JpaRepository<RoleDO, Long> {

    List<RoleDO> findByRoleName(String roleName);
}
