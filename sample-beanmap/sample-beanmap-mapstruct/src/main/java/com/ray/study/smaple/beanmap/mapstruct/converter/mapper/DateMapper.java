package com.ray.study.smaple.beanmap.mapstruct.converter.mapper;

import java.util.Date;

/**
 * description
 *
 * @author r.shi 2021/5/26 16:11
 */
public class DateMapper {

    public Long asLong(Date date) {
        return date == null ? null : date.getTime();
    }

    public Date asDate(Long date) {
        return date == null ? null : new Date(date);
    }

}
