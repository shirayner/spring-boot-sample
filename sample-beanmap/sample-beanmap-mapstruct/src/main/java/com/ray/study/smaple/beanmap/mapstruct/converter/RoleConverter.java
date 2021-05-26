package com.ray.study.smaple.beanmap.mapstruct.converter;

import com.ray.study.smaple.beanmap.mapstruct.dto.RoleDTO;
import com.ray.study.smaple.beanmap.mapstruct.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * description
 *
 * @author r.shi 2021/5/26 15:43
 */
@Mapper(uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleConverter extends BaseConverter<RoleDTO, Role> {

    RoleConverter INSTANCE = Mappers.getMapper(RoleConverter.class);

}
