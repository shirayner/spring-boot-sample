package com.ray.study.smaple.beanmap.mapstruct.converter;

import com.ray.study.smaple.beanmap.mapstruct.dto.ResourceDTO;
import com.ray.study.smaple.beanmap.mapstruct.entity.Resource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * description
 *
 * @author r.shi 2021/5/26 15:43
 */
@Mapper
public interface ResourceConverter {

    ResourceConverter INSTANCE = Mappers.getMapper(ResourceConverter.class);

    @Mapping(source = "resourceCode", target = "code")
    ResourceDTO toD(Resource entity);

    @Mapping(source = "code", target = "resourceCode")
    Resource toE(ResourceDTO dto);

}
