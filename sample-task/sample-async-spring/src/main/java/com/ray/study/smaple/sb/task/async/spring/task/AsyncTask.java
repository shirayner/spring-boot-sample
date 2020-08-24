package com.ray.study.smaple.sb.task.async.spring.task;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * AsyncTask
 *  通过 {link AsyncThreadPoolConfig} 中的 @EnableAsync 注解， 和方法上的  @Async 实现任务的异步调用
 *
 * @author ray
 * @date 2020/8/25
 */
@Component
public class AsyncTask  extends AbstractTask {

    @Async
    @Override
    public void doTaskOne() throws Exception {
        super.doTaskOne();
    }

    @Async
    @Override
    public void doTaskTwo() throws Exception {
        super.doTaskTwo();
    }

    @Async
    @Override
    public void doTaskThree() throws Exception {
        super.doTaskThree();
    }
}