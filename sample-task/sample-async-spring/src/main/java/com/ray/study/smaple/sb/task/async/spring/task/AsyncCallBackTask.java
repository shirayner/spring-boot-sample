package com.ray.study.smaple.sb.task.async.spring.task;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

/**
 * AsyncCallBackTask
 *
 * @author ray
 * @date 2020/8/25
 */
@Component
public class AsyncCallBackTask extends AbstractTask {

    @Async
    public Future<String> doTaskOneCallback() throws Exception {
        super.doTaskOne();
        return new AsyncResult<>("任务一完成");
    }

    @Async
    public Future<String> doTaskTwoCallback() throws Exception {
        super.doTaskTwo();
        return new AsyncResult<>("任务二完成");
    }

    @Async
    public Future<String> doTaskThreeCallback() throws Exception {
        super.doTaskThree();
        return new AsyncResult<>("任务三完成");
    }

}
