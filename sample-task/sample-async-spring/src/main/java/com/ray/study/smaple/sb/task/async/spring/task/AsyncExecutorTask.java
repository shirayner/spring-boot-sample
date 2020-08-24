package com.ray.study.smaple.sb.task.async.spring.task;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * AsyncExecutorTask
 *
 * @author ray
 * @date 2020/8/25
 */
@Component
public class AsyncExecutorTask extends AbstractTask {

    @Async("taskExecutor")
    @Override
    public void doTaskOne() throws Exception {
        super.doTaskOne();
        System.out.println("任务一，当前线程：" + Thread.currentThread().getName());
    }

    @Async("taskExecutor")
    @Override
    public void doTaskTwo() throws Exception {
        super.doTaskTwo();
        System.out.println("任务二，当前线程：" + Thread.currentThread().getName());
    }

    @Async("taskExecutor")
    @Override
    public void doTaskThree() throws Exception {
        super.doTaskThree();
        System.out.println("任务三，当前线程：" + Thread.currentThread().getName());
    }
}
