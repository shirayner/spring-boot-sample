package com.ray.study.smaple.beanmap.mapstruct.converter;

import com.ray.study.smaple.beanmap.mapstruct.converter.mapper.DateMapper;
import com.ray.study.smaple.beanmap.mapstruct.dto.UserDTO;
import com.ray.study.smaple.beanmap.mapstruct.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * description
 *
 * @author r.shi 2021/5/26 15:43
 */
@Mapper(uses = {DateMapper.class, RoleConverter.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy =ReportingPolicy.IGNORE)
public interface UserConverter extends BaseConverter<UserDTO, User> {

    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

}
