package com.ray.study.smaple.beanmap.mapstruct.converter;

import com.ray.study.smaple.beanmap.mapstruct.dto.ResourceDTO;
import com.ray.study.smaple.beanmap.mapstruct.entity.Resource;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * description
 *
 * @author r.shi 2021/5/26 17:18
 */
public class ResourceConverterTest {

    @Test
    public void toD() {
        Resource resource = mockResource();
        ResourceDTO resourceDTO = ResourceConverter.INSTANCE.toD(resource);
        System.out.println(resourceDTO);
        Assert.assertNotNull(resourceDTO.getCode());
    }

    @Test
    public void toE() {
        Resource resource = mockResource();
        ResourceDTO resourceDTO = ResourceConverter.INSTANCE.toD(resource);

        resource = ResourceConverter.INSTANCE.toE(resourceDTO);
        System.out.println(resource);
        Assert.assertNotNull(resource.getResourceCode());
    }

    private static Resource mockResource() {
        Resource resource = new Resource();
        resource.setId(1L);
        resource.setResourceCode("/api/user/list");
        resource.setResourceStatus(Boolean.TRUE);
        return resource;
    }
}