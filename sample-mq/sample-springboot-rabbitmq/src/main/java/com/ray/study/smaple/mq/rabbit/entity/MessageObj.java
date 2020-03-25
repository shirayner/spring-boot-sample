package com.ray.study.smaple.mq.rabbit.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * MessageObj
 *
 * @author ray
 * @date 2020/3/26
 */
@Data
public class MessageObj implements Serializable {

    private static final long serialVersionUID = 5088697673359856350L;

    private Integer id;

    private String name;

    private boolean isAck;

}

