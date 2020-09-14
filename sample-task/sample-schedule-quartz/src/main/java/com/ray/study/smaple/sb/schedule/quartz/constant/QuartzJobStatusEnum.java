package com.ray.study.smaple.sb.schedule.quartz.constant;

/**
 * description
 *
 * @author r.shi 2020/09/11 17:02
 */
public enum QuartzJobStatusEnum {

    READY(0),
    STARTED(1),
    STOPED(2);

    private int status;

    QuartzJobStatusEnum(int status) {
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    }
}
