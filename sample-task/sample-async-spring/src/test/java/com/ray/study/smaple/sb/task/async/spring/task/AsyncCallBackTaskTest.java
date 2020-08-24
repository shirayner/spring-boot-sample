package com.ray.study.smaple.sb.task.async.spring.task;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.*;

/**
 * AsyncCallBackTaskTest
 *
 * @author ray
 * @date 2020/8/25
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class AsyncCallBackTaskTest {

    @Autowired
    private AsyncCallBackTask task;

    @Test
    public void testAsyncCallbackTask() throws Exception {
        long start = System.currentTimeMillis();
        Future<String> task1 = task.doTaskOneCallback();
        Future<String> task2 = task.doTaskTwoCallback();
        Future<String> task3 = task.doTaskThreeCallback();

        // 三个任务都调用完成，退出循环等待
        while (!task1.isDone() || !task2.isDone() || !task3.isDone()) {
            Thread.sleep(1000);
        }

        long end = System.currentTimeMillis();
        System.out.println("任务全部完成，总耗时：" + (end - start) + "毫秒");
    }

}