package com.ray.study.smaple.sb.task.async.spring.task;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * AsyncExecutorTaskTest
 *
 * @author ray
 * @date 2020/8/25
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
class AsyncExecutorTaskTest {
    @Autowired
    private AsyncExecutorTask task;

    @Test
    public void testAsyncExecutorTask() throws Exception {
        task.doTaskOne();
        task.doTaskTwo();
        task.doTaskThree();

        Thread.sleep(30 * 1000L);
    }

}